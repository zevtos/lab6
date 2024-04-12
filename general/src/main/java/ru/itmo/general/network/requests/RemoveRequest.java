package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class RemoveRequest extends Request implements Serializable {
    private final int id;

    public RemoveRequest(int id) {
        super(CommandName.REMOVE_BY_ID);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
