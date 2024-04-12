package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class RemoveFirstRequest extends Request implements Serializable {
    public RemoveFirstRequest() {
        super(CommandName.REMOVE_FIRST);
    }
}
