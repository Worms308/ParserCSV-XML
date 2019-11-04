package parsers;

import java.util.ArrayList;
import java.util.List;

public class InterpreterXML implements Interpreter {
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
