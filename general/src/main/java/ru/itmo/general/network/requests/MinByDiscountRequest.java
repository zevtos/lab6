package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class MinByDiscountRequest extends Request implements Serializable {
    public MinByDiscountRequest() {
        super(CommandName.MIN_BY_DISCOUNT);
    }
}
