package ru.itmo.client.commands.custom;

import ru.itmo.client.commands.Command;
import ru.itmo.client.managers.CommandManager;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;

/**
 * Команда 'history'. Выводит историю использованных команд.
 * @author zevtos
 */
public class History extends Command {
    private final Console console;
    private final CommandManager commandManager;

    /**
     * Конструктор для создания экземпляра команды History.
     *
     * @param console        объект для взаимодействия с консолью
     * @param commandManager менеджер команд
     */
    public History(Console console, CommandManager commandManager, TCPClient tcpClient) {
        super("history", "вывести список использованных команд", tcpClient);
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (в данном случае ожидается отсутствие аргументов)
     * @return Успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        commandManager.getCommandHistory().forEach(console::println);
        return true;
    }
}
