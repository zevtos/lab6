package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class ExitRequest extends Request implements Serializable {
    public ExitRequest() {
        super(CommandName.EXIT);
    }
}
