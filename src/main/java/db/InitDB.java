package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
                "CREATE TABLE customers (id INT NOT NULL,name VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, age int," +
                        "PRIMARY KEY (id))";
        statement.execute(command);
        statement.close();
    }

    private void createContacts() throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("DROP TABLE IF EXISTS contacts");

        String command =
                "CREATE TABLE contacts (id INT NOT NULL,id_customer INT NOT NULL, type VARCHAR(1) NOT NULL, contact VARCHAR(100) NOT NULL," +
                        "PRIMARY KEY (id))";
        statement.execute(command);

        String foreignKey =
                "ALTER TABLE contacts ADD FOREIGN KEY (id_customer) REFERENCES customers(id)";
        statement.execute(foreignKey);
        statement.close();
    }

    private void insertCustomer(Integer id, String name, String surname, Integer age) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO customers VALUES (" + id + ", '" +
                name + "', '" +
                surname + "', " +
                age + ")";
        statement.executeUpdate(customers);
    }

    private void insertContact(Integer id, Integer idCustomer, String type, String contact) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO contacts VALUES (" + id + ", " +
                idCustomer + ", '" +
                type + "', '" +
                contact + "')";
        statement.executeUpdate(customers);
    }

    public boolean fillWithMocks() throws SQLException {
        for (int i = 0; i < 5; ++i) {
            insertCustomer(i, "Jan", "Pawłecki", null);
        }
        Random random = new Random();
        for (int i=0;i<10;++i){
            insertContact(i, random.nextInt(4) + 1, "1", "Skype");
        }
        return true;
    }
}
