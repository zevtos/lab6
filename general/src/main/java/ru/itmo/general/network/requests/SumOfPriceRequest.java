package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class SumOfPriceRequest extends Request implements Serializable {
    public SumOfPriceRequest() {
        super(CommandName.SUM_OF_PRICE);
    }
}
