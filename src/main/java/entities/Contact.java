package entities;

import lombok.Data;

@Data
public class Contact {

    private Integer id;
    private Integer idCustomer;
    private ContactType type;
    private String contact;
}
