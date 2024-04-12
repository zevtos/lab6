package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.network.requests.ExitRequest;

/**
 * Команда 'exit'. Завершает выполнение программы (без сохранения в файл).
 * @author zevtos
 */
public class Exit extends Command {
    private final Console console;

    /**
     * Конструктор для создания экземпляра команды Exit.
     *
     * @param console объект для взаимодействия с консолью
     */
    public Exit(Console console, TCPClient tcpClient) {
        super("exit", "завершить программу (без сохранения в файл)", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (ожидается отсутствие аргументов)
     * @return Успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Завершение выполнения...");
        tcpClient.sendCommand(new ExitRequest());
        return true;
    }
}
