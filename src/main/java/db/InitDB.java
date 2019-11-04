package db;

import db.dao.ContactsDAO;
import db.dao.CustomersDAO;
import entities.Contact;
import entities.ContactType;
import entities.Customer;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class InitDB {

    private Connection connection;

    public boolean initDatabase(String connectionString, String user, String password) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:~/rekrutacja", "sa", "");
            connection = conn;
            createCustomers();
            createContacts();

            fillWithMocks();
            printDB();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void createCustomers() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("DROP TABLE IF EXISTS customers");

        String command =
                "CREATE TABLE customers (id INT AUTO_INCREMENT,name VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, age VARCHAR(3)," +
                        "PRIMARY KEY (id))";
        statement.execute(command);
        statement.close();
    }

    private void createContacts() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("DROP TABLE IF EXISTS contacts");

        String command =
                "CREATE TABLE contacts (id INT AUTO_INCREMENT, id_customer INT NOT NULL, type VARCHAR(1) NOT NULL, contact VARCHAR(100) NOT NULL," +
                        "PRIMARY KEY (id))";
        statement.execute(command);

        String foreignKey =
                "ALTER TABLE contacts ADD FOREIGN KEY (id_customer) REFERENCES customers(id)";
        statement.execute(foreignKey);
        statement.close();
    }

    public boolean fillWithMocks() throws SQLException {
        CustomersDAO customersDAO = new CustomersDAO();
        for (int i = 0; i < 5; ++i) {
            customersDAO.insert("Jan", "Pawłecki", i*5);
        }

        Random random = new Random();
        ContactsDAO contactsDAO = new ContactsDAO();
        for (int i=0;i<10;++i){
            contactsDAO.insert(random.nextInt(5) + 1, ContactType.JABBER, "Skype");
        }

        return true;
    }

    public void printDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM customers");

        CustomersDAO customersDAO = new CustomersDAO();
        List<Customer> customers = customersDAO.selectAll();
        customers.forEach(System.err::println);

        System.err.println("----------------------");

        ContactsDAO contactsDAO = new ContactsDAO();
        List<Contact> contacts = contactsDAO.selectAll();
        contacts.forEach(System.err::println);
    }
}
