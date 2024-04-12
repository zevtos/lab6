package ru.itmo.general.network.responses;

public class RemoveResponse extends Response {
    public RemoveResponse(boolean success, String message) {
        super(success, message, null);
    }
}