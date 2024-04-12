package ru.itmo.general.network.responses;

public class AddPersonResponse extends Response {
    public AddPersonResponse(boolean success, String message) {
        super(success, message, null);
    }
}