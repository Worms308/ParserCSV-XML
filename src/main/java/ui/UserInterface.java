package ui;

import entities.Contact;
import entities.Customer;
import entities.dao.ContactsDAO;
import entities.dao.CustomersDAO;
import exceptions.ExtensionNotSupportedException;
import service.ParsersMediator;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private ParsersMediator parsersMediator;
    private boolean isRunning = true;

    public UserInterface(ParsersMediator parsersMediator) {
        this.parsersMediator = parsersMediator;
    }

    private boolean loadFile() {
        System.out.print("Wprowadź nazwę pliku: ");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();

        try {
            parsersMediator.loadFile(filename);
        } catch (FileNotFoundException e) {
            System.err.println("Plik [" + filename + "] nie znaleziony!");
            return false;
        } catch (ExtensionNotSupportedException ex) {
            System.err.println("Plik [" + filename + "] posiada nieobsługiwane rozszerzenie!");
            return false;
        }
        return true;
    }

    private String commandPrompt(Scanner scanner) {
        System.out.print(">> ");
        return scanner.next();
    }

    private void printCommandState(boolean commandState) {
        if (commandState)
            System.out.println("Operacja powiodła się.\n");
        else
            System.err.println("Operacja nie powiodła się!\n");
    }

    private boolean printAllCustomers() {
        try (CustomersDAO customersDAO = new CustomersDAO()) {
            List<Customer> customers = customersDAO.selectAll();
            this.printPersons(customers);
        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych.");
            return false;
        }
        return true;
    }

    private void printPersons(List<Customer> customers) {
        System.out.println("  ID            IMIE        NAZWISKO  WIEK          MIASTO");
        for (Customer it : customers) {
            System.out.printf("%4d %15s %15s %5s %15s\n", it.getId(), it.getName(), it.getSurname(), it.getAge(), it.getCity());
        }
    }

    private boolean printAllContacts() {
        try (ContactsDAO contactsDAO = new ContactsDAO()) {
            List<Contact> contacts = contactsDAO.selectAll();
            this.printContacts(contacts);
        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych.");
            return false;
        }
        return true;
    }

    private void printContacts(List<Contact> contacts) {
        System.out.println("  ID CUSTOMER_ID      TYP              KONTAKT");
        for (Contact it : contacts) {
            System.out.printf("%4d %11s %8s %20s\n", it.getId(), it.getIdCustomer(), it.getType(), it.getContact());
        }
    }

    private boolean printPersonWithContacts(Scanner scanner) {
        try (CustomersDAO customersDAO = new CustomersDAO(); ContactsDAO contactsDAO = new ContactsDAO()) {
            System.out.print("Podaj ID szukanej osoby: ");
            int id = scanner.nextInt();
            Customer customer = customersDAO.selectById(id);
            List<Contact> contacts = contactsDAO.selectByCustomer(customer);
            List<Customer> customers = new ArrayList<>();
            customers.add(customer);

            this.printPersons(customers);
            this.printContacts(contacts);
        } catch (NullPointerException ex) {
            System.err.println("Brak osoby o podanym ID.");
            return false;
        } catch (InputMismatchException ex) {
            System.err.println("Niepoprawny ID.");
            return false;
        } catch (SQLException e) {
            System.err.println("Błąd połączenia z bazą danych.");
            return false;
        }
        return true;
    }

//    private boolean createQuery(Scanner scanner){
//        System.out.println("Wprowadź zapytanie SQL zgodne z dialektem bazy danych H2 w wersji \"1.4.197\"");
//        String query = scanner.next();
//        InitDB db = new InitDB();
//        try {
//            ResultSet set = db.createQuery(query);
//            while (set.next()){
//                System.out.println(set);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Błąd SQL: " + e.getStackTrace());
//            return false;
//        }
//        return true;
//    }

    private void printMenu() {
        System.out.println(" --- Wczytywanie plików do bazy danych --- ");
        System.out.println(" 1. Wczytaj plik do bazy danych.");
        System.out.println(" 2. Wyświetl dane z tabeli Customers");
        System.out.println(" 3. Wyświetl dane z tabeli Contacts");
        System.out.println(" 4. Wyświetl osobę wraz z jego kontaktami.");
//        System.out.println(" 5. Ręczne wprowadzanie zapytań SQL. (Niezalecane)");
    }

    public void mainLoop() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (isRunning) {
            this.printMenu();
            command = commandPrompt(scanner);

            switch (command.trim()) {
                case "1":
                    this.printCommandState(this.loadFile());
                    break;
                case "2":
                    this.printCommandState(this.printAllCustomers());
                    break;
                case "3":
                    this.printCommandState(this.printAllContacts());
                    break;
                case "4":
                    this.printCommandState(this.printPersonWithContacts(scanner));
                    break;
//                case "5":
//                    this.printCommandState(this.createQuery(scanner));
//                    break;
                default:
                    System.err.println("Komenda nie rozpoznana!");
            }
            scanner.nextLine();
        }
        scanner.close();
    }
}
