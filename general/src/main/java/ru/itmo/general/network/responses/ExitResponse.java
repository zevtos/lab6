package ru.itmo.general.network.responses;

public class ExitResponse extends Response {
    public ExitResponse(boolean success, String message) {
        super(success, message, null);
    }
}