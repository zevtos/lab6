package ru.itmo.server.commands.custom;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.models.Ticket;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.MaxByNameResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'max_by_name'. Выводит элемент с максимальным именем.
 * @author zevtos
 */
public class MaxByName extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public MaxByName(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("max_by_name", "вывести любой объект из коллекции, значение поля name которого является максимальным", tcpServer);
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
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();
            Ticket ticket = maxByName();
            return new MaxByNameResponse(true, null, ticket.toString());

        }  catch (EmptyValueException exception) {
            return new MaxByNameResponse(false, "Коллекция пуста!", null);
        }
    }

    private Ticket maxByName() {
        String maxName = "";
        int ticketId = -1;
        for (Ticket c : ticketCollectionManager.getCollection()) {
            if (c.getName().compareTo(maxName) < 0) {
                maxName = c.getName();
                ticketId = c.getId();
            }
        }
        if (ticketId == -1) return ticketCollectionManager.getFirst();
        return ticketCollectionManager.byId(ticketId);
    }
}
