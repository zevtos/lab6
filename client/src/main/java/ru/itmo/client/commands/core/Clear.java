package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.network.requests.ClearRequest;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author zevtos
 */
public class Clear extends Command {
    private final Console console;


    public Clear(Console console, TCPClient tcpClient) {
        super("clear", "очистить коллекцию", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        tcpClient.sendCommand(new ClearRequest());

        console.println("Коллекция очищена!");
        return true;
    }
}
