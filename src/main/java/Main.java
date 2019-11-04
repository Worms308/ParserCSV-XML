import db.InitDB;
import db.dao.ContactsDAO;
import db.dao.CustomersDAO;
import parsers.InterpreterCSV;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        InitDB initDB = new InitDB();
        initDB.initDatabase("jdbc:h2:~/rekrutacja", "sa","");

        InterpreterCSV interpreterCSV = new InterpreterCSV();
        interpreterCSV.parseFile("dane-osoby.txt");

        CustomersDAO customersDAO = new CustomersDAO();
        customersDAO.selectAll().forEach(System.out::println);

        System.out.println("----");

        ContactsDAO contactsDAO = new ContactsDAO();
        contactsDAO.selectAll().forEach(System.out::println);


    }
}
