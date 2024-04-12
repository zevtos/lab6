package ru.itmo.general.network.responses;

public class UpdateResponse extends Response {
    public UpdateResponse(boolean success, String message) {
        super(success, message, null);
    }
}