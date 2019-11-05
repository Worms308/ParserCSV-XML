package db;

import entities.dao.ContactsDAO;
import entities.dao.CustomersDAO;
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
            connection = DriverManager.getConnection("jdbc:h2:~/rekrutacja", "sa", "");
            createCustomers();
            createContacts();
            connection.close();
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
                "CREATE TABLE customers (id INT AUTO_INCREMENT,name VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, age VARCHAR(3), city VARCHAR(50) NOT NULL, " +
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

}
