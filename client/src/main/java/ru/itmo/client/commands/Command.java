package ru.itmo.client.commands;


import ru.itmo.client.network.TCPClient;

import java.util.Objects;

/**
 * Абстрактная команда с именем и описанием.
 *
 * @author zevtos
 */
public abstract class Command implements Describable, Executable {
    private final String name;
    private final String description;
    protected final TCPClient tcpClient;

    /**
     * Конструктор для создания команды с именем и описанием.
     *
     * @param name        Название команды.
     * @param description Описание команды.
     */
    public Command(String name, String description, TCPClient tcpClient) {
        this.name = name;
        this.description = description;
        this.tcpClient = tcpClient;
    }

    /**
     * Получить название команды.
     *
     * @return Название команды.
     */
    public String getName() {
        return name;
    }

    /**
     * Получить описание команды.
     *
     * @return Описание команды.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) && Objects.equals(description, command.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
