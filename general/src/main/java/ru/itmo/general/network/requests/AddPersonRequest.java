package ru.itmo.general.network.requests;

import ru.itmo.general.models.Person;
import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class AddPersonRequest extends Request implements Serializable {
    private final Person person;

    public AddPersonRequest(Person person) {
        super(CommandName.ADD_PERSON);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
