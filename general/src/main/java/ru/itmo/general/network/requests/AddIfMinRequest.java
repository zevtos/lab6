package ru.itmo.general.network.requests;

import ru.itmo.general.models.Ticket;
import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class AddIfMinRequest extends Request implements Serializable {
    private final Ticket ticket;

    public AddIfMinRequest(Ticket ticket) {
        super(CommandName.ADD_IF_MIN);
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
