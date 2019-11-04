package db.dao;

import entities.Contact;
import entities.ContactType;
import entities.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactsDAO extends Dao {

    public ContactsDAO() throws SQLException {

    }

    private Contact createContact(ResultSet set) throws SQLException {
        Contact contact = new Contact();
        contact.setId(set.getInt(1));
        contact.setIdCustomer(set.getInt(2));
        int typeNum = Integer.parseInt(set.getString(3));
        switch (typeNum){
            case 1:
                contact.setType(ContactType.EMAIL);
                break;
            case 2:
                contact.setType(ContactType.PHONE);
                break;
            case 3:
                contact.setType(ContactType.JABBER);
                break;
            default:
                contact.setType(ContactType.UNKNOWN);
        }
        contact.setContact(set.getString(4));
        return contact;
    }

    public void insert(Integer idCustomer, ContactType type, String contact) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO contacts(id_customer, type, contact) VALUES (" +
                idCustomer + ", '" +
                type.getValue() + "', '" +
                contact + "')";
        statement.executeUpdate(customers);
        statement.close();
    }

    public void insert(Contact contact) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO contacts(id_customer, type, contact) VALUES (" +
                contact.getIdCustomer() + ", '" +
                contact.getType().getValue() + "', '" +
                contact.getContact() + "')";
        statement.executeUpdate(customers);
        statement.close();
    }

    public List<Contact> selectAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM contacts");

        List<Contact> contactList = new ArrayList<>();
        while (set.next()){
            contactList.add(createContact(set));
        }
        statement.close();

        return contactList;
    }

    public Contact selectById(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM contacts WHERE id = " + id);

        Contact contact = null;
        if (set.next())
            contact = createContact(set);

        statement.close();

        return contact;
    }

    public List<Contact> selectByCustomer(Customer customer) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM contacts WHERE id_customer = " + customer.getId());

        List<Contact> contactList = new ArrayList<>();
        while (set.next()){
            contactList.add(createContact(set));
        }
        statement.close();

        return contactList;
    }
}
