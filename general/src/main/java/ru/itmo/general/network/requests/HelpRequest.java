package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class HelpRequest extends Request implements Serializable {
    public HelpRequest() {
        super(CommandName.HELP);
    }
}
