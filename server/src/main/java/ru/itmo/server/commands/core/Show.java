package ru.itmo.server.commands.core;

import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.Response;
import ru.itmo.general.network.responses.ShowResponse;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * @author zevtos
 */
public class Show extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Show.
     *
     * @param ticketCollectionManager менеджер коллекции
     */
    public Show(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("show", "вывести все элементы коллекции Ticket", tcpServer);
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     *
     * @param arguments аргументы команды
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request arguments) {

        String message = ticketCollectionManager.toString();
        return new ShowResponse(true, null, message);
    }
}
