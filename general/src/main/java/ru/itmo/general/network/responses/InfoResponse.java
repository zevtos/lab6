package ru.itmo.general.network.responses;

public class InfoResponse extends Response {
    public InfoResponse(boolean success, String message, String info) {
        super(success, message, info);
    }

    public String getInfo() {
        return (String) getData();
    }
}