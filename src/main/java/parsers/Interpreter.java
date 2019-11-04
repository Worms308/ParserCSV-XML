package parsers;

import entities.Contact;
import entities.Customer;

import java.io.FileNotFoundException;
import java.util.List;

public interface Interpreter {
    void parseFile(String filename) throws FileNotFoundException;
    List<String> getExtensions();
}
