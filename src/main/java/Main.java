import db.InitDB;
import entities.dao.ContactsDAO;
import entities.dao.CustomersDAO;
import parsers.ParserCSV;
import parsers.ParserXML;
import service.ParsersMediator;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        InitDB initDB = new InitDB();
        initDB.initDatabase("jdbc:h2:~/rekrutacja", "sa","");


        ParsersMediator mediator = new ParsersMediator();
        mediator.addParser(new ParserCSV());
        mediator.addParser(new ParserXML());
        mediator.loadFile("dane-osoby.xml");
        mediator.loadFile("dane-osoby.txt");
        long start = System.nanoTime();
        mediator.loadFile("dane-osoby-copy.xml");
        long end = System.nanoTime();

        CustomersDAO customersDAO = new CustomersDAO();
//        customersDAO.selectAll().forEach(System.out::println);

        System.out.println("----");

        ContactsDAO contactsDAO = new ContactsDAO();
//        contactsDAO.selectAll().forEach(System.out::println);


        System.err.println("Czas dodawania danych: " + (end - start)/1000000000.0 + "s");
    }
}
