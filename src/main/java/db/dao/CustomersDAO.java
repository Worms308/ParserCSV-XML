package db.dao;

import entities.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomersDAO extends Dao {

    public CustomersDAO() throws SQLException {

    }

    private Customer createCustomer(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt(1));
        customer.setName(resultSet.getString(2));
        customer.setSurname(resultSet.getString(3));
        customer.setAge(resultSet.getString(4));
        return customer;
    }

    public void insert(String name, String surname, Integer age) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO customers(name, surname, age) VALUES ('" +
                name + "', '" +
                surname + "', " +
                age + ")";
        statement.executeUpdate(customers);
        statement.close();
    }

    public void insert(Customer customer) throws SQLException {
        Statement statement = connection.createStatement();
        String customers = "INSERT INTO customers(name, surname, age) VALUES ('" +
                customer.getName() + "', '" +
                customer.getSurname() + "', " +
                customer.getAge() + ")";
        statement.executeUpdate(customers);
        statement.close();
    }

    public List<Customer> selectAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM customers");

        List<Customer> resultSet = new ArrayList<>();
        while (set.next()){
            resultSet.add(createCustomer(set));
        }
        statement.close();

        return resultSet;
    }

    public Customer selectById(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT * FROM customers WHERE id =" + id);

        Customer resultSet = null;
        if (set.next())
            resultSet = createCustomer(set);

        statement.close();

        return resultSet;
    }

}
