package parsers;

import entities.Contact;
import entities.ContactType;
import entities.Customer;
import entities.dao.ContactsDAO;
import entities.dao.CustomersDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserXML implements Parser {

    private String getTag(String line) {
        StringBuilder result = new StringBuilder();
        int offset = 0;
        while (line.charAt(offset) != '<') offset++;
        offset++;
        while (line.charAt(offset) != ' ' && line.charAt(offset) != '>') {
            result.append(line.charAt(offset));
            offset++;
        }
        return result.toString();
    }

    private String getTagValue(String line) {
        int start = line.indexOf(">") + 1;
        int end = line.lastIndexOf("<");
        return line.substring(start, end);
    }

    private String getAttributeValue(String line, String attribute) {
        final int index = line.indexOf(attribute);
        if (index == -1) return "";
        int distance = attribute.length();
        while (line.charAt(index + distance) != '\"')
            distance++;
        distance++;

        StringBuilder result = new StringBuilder();
        int offset = 0;
        while (line.charAt(offset + distance + index) != '\"') {
            result.append(line.charAt(offset + distance + index));
            offset++;
        }

        return result.toString();
    }

    ///######################################################################

    private void setParameter(Customer customer, String line) {
        switch (getTag(line)) {
            case "name":
                customer.setName(getTagValue(line));
                break;
            case "surname":
                customer.setSurname(getTagValue(line));
                break;
            case "age":
                customer.setAge(getTagValue(line));
                break;
            case "city":
                customer.setCity(getTagValue(line));
                break;
        }
    }

    @Override
    public void parseFile(String filename) {
        File file = new File(filename);
        if (!file.exists() || file.isDirectory())
            return;

        try (Scanner scanner = new Scanner(file)) {
            String line;

            if (!scanner.hasNextLine())
                return;
            line = scanner.nextLine();
            if (!getAttributeValue(line, "version").equals("1.0") || !getAttributeValue(line, "encoding").equals("UTF-8"))
                return;
            if (!(scanner.hasNextLine() && getTag(scanner.nextLine()).equals("persons")))
                return;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (getTag(line).equals("person")) {
                    this.insertPerson(scanner);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertPerson(Scanner scanner) throws SQLException {
        CustomersDAO customersDAO = new CustomersDAO();
        ContactsDAO contactsDAO = new ContactsDAO();
        String line;
        Customer customer = new Customer();
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (getTag(line).equals("/person"))
                break;

            this.setParameter(customer, line);

            if (getTag(line).equals("contacts")){
                customersDAO.insert(customer);
                int personId = customersDAO.getLastInsertedId();
                while (scanner.hasNextLine()){
                    line = scanner.nextLine();
                    if (getTag(line).equals("/contacts"))
                        break;

                    Contact contact = createContact(line, personId);
                    contactsDAO.insert(contact);
                }
            }

        }
    }

    private Contact createContact(String line, int customerID) {
        Contact contact = new Contact();
        contact.setIdCustomer(customerID);
        contact.setContact(getTagValue(line));

        String type = getTag(line);
        switch (type) {
            case "phone":
                contact.setType(ContactType.PHONE);
                break;
            case "email":
                contact.setType(ContactType.EMAIL);
                break;
            case "jabber":
                contact.setType(ContactType.JABBER);
                break;
            default:
                contact.setType(ContactType.UNKNOWN);
        }

        return contact;
    }

    @Override
    public List<String> getExtensions() {
        List<String> extensions = new ArrayList<>();
        extensions.add(".xml");
        return extensions;
    }
}
