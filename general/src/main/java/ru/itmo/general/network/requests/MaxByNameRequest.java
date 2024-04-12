package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class MaxByNameRequest extends Request implements Serializable {
    public MaxByNameRequest() {
        super(CommandName.MAX_BY_NAME);
    }
}
