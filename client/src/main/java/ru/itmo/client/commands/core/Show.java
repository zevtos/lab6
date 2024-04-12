package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.network.requests.ShowRequest;
import ru.itmo.general.network.responses.ShowResponse;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * @author zevtos
 */
public class Show extends Command {
    private final Console console;


    /**
     * Конструктор для создания экземпляра команды Show.
     *
     * @param console объект для взаимодействия с консолью
     * @param  tcpClient
     */
    public Show(Console console, TCPClient tcpClient) {
        super("show", "вывести все элементы коллекции Ticket", tcpClient);
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
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        ShowResponse resp = (ShowResponse) tcpClient.sendCommand(new ShowRequest());
        if(!(resp == null)) System.out.println(resp.getTicketsInfo());
        else{
            System.out.println("[]");
        }
        return true;
    }
}
