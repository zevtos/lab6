package ru.itmo.general.network.requests;

import ru.itmo.general.models.Ticket;
import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public class UpdateRequest extends Request implements Serializable {
    private final int id;
    private final Ticket ticket;

    public UpdateRequest(int id, Ticket ticket) {
        super(CommandName.UPDATE);
        this.id = id;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
