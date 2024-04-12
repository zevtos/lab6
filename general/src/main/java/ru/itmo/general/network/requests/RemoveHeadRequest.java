package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class RemoveHeadRequest extends Request implements Serializable {
    public RemoveHeadRequest() {
        super(CommandName.REMOVE_HEAD);
    }
}
