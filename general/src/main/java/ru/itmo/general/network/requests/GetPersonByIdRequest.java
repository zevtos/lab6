package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class GetPersonByIdRequest extends Request implements Serializable {
    private final String personId;

    public GetPersonByIdRequest(String personId) {
        super(CommandName.GET_PERSON_BY_ID);
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }
}
