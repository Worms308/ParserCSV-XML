package parsers;

import entities.Contact;
import entities.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ParserCSV implements Parser {

    private List<String[]> loadCSV(String filename, String separator) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        if (!scanner.hasNextLine())
            throw new FileNotFoundException("Empty file: " + filename);

        List<String[]> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] tmp = scanner.nextLine().split(separator);
            result.add(tmp);
        }

        scanner.close();

        return result;
    }

    private Customer createCustomer(String[] data) {
        Customer result = new Customer();
        result.setName(data[0]);
        result.setSurname(data[1]);
        try {
            Double age = Double.parseDouble(data[2]);
            result.setAge(age);
        } catch (NumberFormatException ex) {
            result.setAge(null);
        }

        return result;
    }

    private List<Contact> createContacts(Integer customerId, String[] data) {
        List<Contact> result = new ArrayList<>();

        for (int i = 3; i < data.length; ++i) {
            Contact tmp = new Contact();
            tmp.setIdCustomer(customerId);
            tmp.setContact(data[i]);
        }

        return result;
    }

    @Override
    public void parseFile(String filename, List<Customer> customers, List<Contact> contacts) throws FileNotFoundException {
        List<String[]> loadedFile = loadCSV(filename, ",");

        Iterator<String[]> it = loadedFile.iterator();
        while (it.hasNext()) {

        }
    }

    @Override
    public List<String> getExtensions() {
        List<String> extensions = new ArrayList<>();
        extensions.add(".txt");
        extensions.add(".csv");
        return extensions;
    }
}
