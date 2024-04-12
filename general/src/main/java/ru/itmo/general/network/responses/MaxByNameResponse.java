package ru.itmo.general.network.responses;

public class MaxByNameResponse extends Response {
    public MaxByNameResponse(boolean success, String message, String ticketInfo) {
        super(success, message, ticketInfo);
    }

    public String getTicketInfo() {
        return (String) getData();
    }
}