package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;

/**
 * Команда 'execute_script'. Выполнить скрипт из файла.
 * @author zevtos
 */
public class ExecuteScript extends Command {
    private final Console console;

    public ExecuteScript(Console console, TCPClient tcpClient) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Выполнение скрипта '" + arguments[1] + "'...");
        return true;
    }
}
