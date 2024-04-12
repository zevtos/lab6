package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.managers.CommandManager;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;


/**
 * Команда 'help'. Выводит справку по доступным командам.
 * @author zevtos
 */
public class Help extends Command {
    private final Console console;
    private final CommandManager commandManager;

    /**
     * Конструктор для создания экземпляра команды Help.
     *
     * @param console        объект для взаимодействия с консолью
     * @param commandManager менеджер команд
     */
    public Help(Console console, CommandManager commandManager, TCPClient tcpClient) {
        super("help", "вывести справку по доступным командам", tcpClient);
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

        console.println("Справка по командам:");
        commandManager.getCommands().values().forEach(command -> {
            console.printTable(command.getName(), command.getDescription());
        });

        return true;
    }
}
