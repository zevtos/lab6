package ru.itmo.general.network.responses;

public class MinByDiscountResponse extends Response {
    public MinByDiscountResponse(boolean success, String message, String ticketInfo) {
        super(success, message, ticketInfo);
    }

    public String getTicketInfo() {
        return (String) getData();
    }
}