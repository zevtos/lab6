package ru.itmo.general.network.responses;

public class ClearResponse extends Response {
    public ClearResponse(boolean success, String message) {
        super(success, message, null);
    }
}