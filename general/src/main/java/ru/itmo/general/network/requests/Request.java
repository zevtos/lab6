package ru.itmo.general.network.requests;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;
import java.util.Objects;

public abstract class Request implements Serializable {
    private final String command;

    public Request(CommandName name) {
        this.command = name.toString().toLowerCase();
    }

    public String getCommand() {
        return command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(command, request.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                '}';
    }
}
