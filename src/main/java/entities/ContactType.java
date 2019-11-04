package entities;

public enum ContactType {
    UNKNOWN(0),
    EMAIL(1),
    PHONE(2),
    JABBER(3);

    private int value;

    ContactType(int value){
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
