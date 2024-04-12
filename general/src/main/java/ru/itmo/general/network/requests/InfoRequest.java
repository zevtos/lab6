package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class InfoRequest extends Request implements Serializable {
    public InfoRequest() {
        super(CommandName.INFO);
    }
}
