package parsers;

import entities.Contact;
import entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class ParserXML implements Parser {
    @Override
    public void parseFile(String filename) {

    }

    @Override
    public List<String> getExtensions() {
        List<String> extensions = new ArrayList<>();
        extensions.add(".xml");
        return extensions;
    }
}
