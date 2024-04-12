package ru.itmo.general.network.requests;


import ru.itmo.general.models.Ticket;
import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class AddRequest extends Request implements Serializable {
    private final Ticket ticket;

    public AddRequest(Ticket ticket) {
        super(CommandName.ADD);
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
