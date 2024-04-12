package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class ShowRequest extends Request implements Serializable {
    public ShowRequest() {
        super(CommandName.SHOW);
    }
}
