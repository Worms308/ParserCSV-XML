package entities;

import lombok.Data;

import java.util.List;

@Data
public class Customer {

    private Integer id;
    private String name;
    private String surname;
    private String age;
//    private List<Contact> contacts;
}
