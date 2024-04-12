package ru.itmo.general.network.responses;

public class AddResponse extends Response {
    public AddResponse(boolean success, String message, int newId) {
        super(success, message, newId);
    }

    public int getNewId() {
        return (int) getData();
    }
}