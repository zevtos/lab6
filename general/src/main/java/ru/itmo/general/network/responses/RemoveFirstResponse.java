package ru.itmo.general.network.responses;

public class RemoveFirstResponse extends Response {
    public RemoveFirstResponse(boolean success, String message) {
        super(success, message, null);
    }
}