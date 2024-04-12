package ru.itmo.general.network.responses;

public class CommandNotFoundResponse extends Response {
    public CommandNotFoundResponse(String name) {
        super(false, "No such command", null);
    }
}