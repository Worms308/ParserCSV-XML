package parsers.utils;

import entities.ContactType;

public class ParserContact {
    public ContactType parse(String contact){
        contact = contact.trim();
        contact.replace(" ", ""); //Space removing
        if (contact.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"))
            return ContactType.EMAIL;
        if (contact.matches("(?<!\\w)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)"))
            return ContactType.PHONE;
        if (contact.matches("^(?:([^@/<>'\\\"]+)@)?([^@/<>'\\\"]+)(?:/([^<>'\\\"]*))?$"))
            return ContactType.JABBER;
        return ContactType.UNKNOWN;
    }
}
