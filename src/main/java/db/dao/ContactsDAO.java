package db.dao;

import java.sql.SQLException;
import java.sql.Statement;

public class ContactsDAO extends Dao {

    public ContactsDAO() throws SQLException {

    }

    public void insert(Integer idCustomer, String type, String contact) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO contacts(id_customer, type, contact) VALUES (" +
                idCustomer + ", '" +
                type + "', '" +
                contact + "')";
        statement.executeUpdate(customers);
        statement.close();
    }

    public void selectAll(){

    }

    public void selectById(){

    }

    public void selectByCustomer(){

    }
}
