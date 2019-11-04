package db;

import java.sql.*;
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
                "CREATE TABLE customers (id INT AUTO_INCREMENT,name VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, age int," +
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

    private void insertCustomer(String name, String surname, Integer age) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO customers(name, surname, age) VALUES ('" +
                name + "', '" +
                surname + "', " +
                age + ")";
        statement.executeUpdate(customers);
//        ResultSet set = statement.executeQuery("SELECT LAST_INSERT_ID() id");
//        if (set.next())
//            System.err.println(set.getInt(1));
    }

    private void insertContact(Integer idCustomer, String type, String contact) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO contacts(id_customer, type, contact) VALUES (" +
                idCustomer + ", '" +
                type + "', '" +
                contact + "')";
        statement.executeUpdate(customers);
    }

    public boolean fillWithMocks() throws SQLException {
        for (int i = 0; i < 5; ++i) {
            insertCustomer("Jan", "Pawłecki", i*5);
        }
        Random random = new Random();
        for (int i=0;i<10;++i){
            insertContact(random.nextInt(5) + 1, "1", "Skype");
        }

        return true;
    }

    public void printDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM customers");
        while (set.next()){
            System.err.println(set.getInt(1) + " " + set.getString(2) +
                    " " + set.getString(3) + " " + set.getString(4));
        }
        statement.close();

        System.err.println("----------------------");

        statement = connection.createStatement();
        set = statement.executeQuery("SELECT * FROM contacts");
        while (set.next()){
            System.err.println(set.getInt(1) + " " + set.getInt(2) +
                    " " + set.getString(3) + " " + set.getString(4));
        }
        statement.close();
    }
}
