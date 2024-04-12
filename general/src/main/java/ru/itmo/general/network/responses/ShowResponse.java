package ru.itmo.general.network.responses;

public class ShowResponse extends Response {
    public ShowResponse(boolean success, String message, String tickets) {
        super(success, message, tickets);
    }

    public String getTicketsInfo() {
        return (String) getData();
    }
}