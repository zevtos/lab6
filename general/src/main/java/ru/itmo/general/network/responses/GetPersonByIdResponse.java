package ru.itmo.general.network.responses;

import ru.itmo.general.models.Person;

import java.io.Serializable;

public class GetPersonByIdResponse extends Response implements Serializable {
    private final Person person;

    public GetPersonByIdResponse(boolean success, String message, Object data, Person person) {
        super(success, message, data);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
