package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.general.network.requests.InfoRequest;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.network.responses.InfoResponse;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author zevtos
 */
public class Info extends Command {
    private final Console console;
    

    /**
     * Конструктор для создания экземпляра команды Info.
     *
     * @param console объект для взаимодействия с консолью
     * @param tcpClient
     */
    public Info(Console console, TCPClient tcpClient) {
        super("info", "вывести информацию о коллекции", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @param arguments аргументы команды
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        InfoResponse resp = (InfoResponse) tcpClient.sendCommand(new InfoRequest());

        System.out.println(resp.getInfo());
        return true;
    }
}
