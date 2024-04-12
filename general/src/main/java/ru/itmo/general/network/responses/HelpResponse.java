package ru.itmo.general.network.responses;

import java.util.List;

public class HelpResponse extends Response {
    public HelpResponse(boolean success, String message, List<String> helpCommands) {
        super(success, message, helpCommands);
    }

    public List<String> getHelpCommands() {
        return (List<String>) getData();
    }
}