package ru.itmo.server.commands.custom;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.exceptions.NotFoundException;
import ru.itmo.general.models.Ticket;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.RemoveHeadResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'remove_head'. Выводит первый элемент коллекции и удаляет его.
 * @author zevtos
 */
public class RemoveHead extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public RemoveHead(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("remove_head", "вывести первый элемент коллекции и удалить его", tcpServer);
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
            Ticket ticket = ticketCollectionManager.getFirst();

            var flag = ticketCollectionManager.remove(ticket);
            return new RemoveHeadResponse(true, "Билет успешно удален.");

        }catch (EmptyValueException exception) {
            return new RemoveHeadResponse(false, "Коллекция пуста!");
        }
    }
}
