package ru.itmo.general.network.responses;

public class RemoveHeadResponse extends Response {
    public RemoveHeadResponse(boolean success, String message) {
        super(success, message, null);
    }
}