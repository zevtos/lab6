package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class ClearRequest extends Request implements Serializable {
    public ClearRequest() {
        super(CommandName.CLEAR);
    }
}
