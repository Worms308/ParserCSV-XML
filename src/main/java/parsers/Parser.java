package parsers;

import java.io.FileNotFoundException;
import java.util.List;

public interface Parser {
    void parseFile(String filename) throws FileNotFoundException;
    List<String> getExtensions();
}
