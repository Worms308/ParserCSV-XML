package parsers;

import db.dao.ContactsDAO;
import db.dao.CustomersDAO;
import entities.Contact;
import entities.Customer;
import parsers.utils.ParserContact;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class InterpreterCSV implements Interpreter {

    private List<String[]> loadCSV(String filename, String separator) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        if (!scanner.hasNextLine())
            throw new FileNotFoundException("Empty file: " + filename);

        List<String[]> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] tmp = scanner.nextLine().split(separator);
            result.add(tmp);
        }

        scanner.close();

        return result;
    }

    private Customer createCustomer(String[] data) {
        Customer result = new Customer();
        result.setName(data[0]);
        result.setSurname(data[1]);
        result.setAge(data[2].length() > 0 ? data[2] : null);
        result.setCity(data[3]);

        return result;
    }

    private List<Contact> createContacts(Integer customerId, String[] data) {
        List<Contact> result = new ArrayList<>();
        ParserContact parserContact = new ParserContact();
        for (int i = 4; i < data.length; ++i) {
            Contact tmp = new Contact();

            tmp.setIdCustomer(customerId);
            tmp.setType(parserContact.parse(data[i]));
            tmp.setContact(data[i]);

            result.add(tmp);
        }
        return result;
    }

    @Override
    public void parseFile(String filename) throws FileNotFoundException {
        List<String[]> loadedFile = loadCSV(filename, ",");

        try {
            CustomersDAO customersDAO = new CustomersDAO();
            ContactsDAO contactsDAO = new ContactsDAO();
            Iterator<String[]> it = loadedFile.iterator();
            while (it.hasNext()) {
                String[] row = it.next();
                Customer tmp = createCustomer(row);
                customersDAO.insert(tmp);
                int id = customersDAO.getLastInsertedId();

                List<Contact> contactList = createContacts(id, row);
                contactList.forEach(contact -> {
                    try {
                        contactsDAO.insert(contact);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public List<String> getExtensions() {
        List<String> extensions = new ArrayList<>();
        extensions.add(".txt");
        extensions.add(".csv");
        return extensions;
    }
}
