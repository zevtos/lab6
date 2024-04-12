package ru.itmo.server.commands.core;

import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.ClearResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.commands.Command;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'clear'. Очищает коллекцию.
 *
 * @author zevtos
 */
public class Clear extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public Clear(TicketCollectionManager ticketCollectionManager, TCPServer tcpServer) {
        super("clear", "очистить коллекцию", tcpServer);
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request arguments) {
        try {
            ticketCollectionManager.clearCollection();
            return new ClearResponse(true, "Коллекция очищена!");
        } catch (Exception e){
            return new ClearResponse(false, e.toString());
        }
    }
}
