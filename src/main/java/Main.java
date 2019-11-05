import db.InitDB;
import entities.dao.ContactsDAO;
import entities.dao.CustomersDAO;
import parsers.ParserCSV;
import parsers.ParserXML;
import service.ParsersMediator;
import ui.UserInterface;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        InitDB initDB = new InitDB();
        initDB.initDatabase("jdbc:h2:~/rekrutacja", "sa","");


        ParsersMediator mediator = new ParsersMediator();
        mediator.addParser(new ParserCSV());
        mediator.addParser(new ParserXML());

        UserInterface userInterface = new UserInterface(mediator);
        userInterface.mainLoop();
    }
}
