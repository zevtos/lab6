package ru.itmo.server.commands.custom;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.exceptions.NotFoundException;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.RemoveFirstResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 * @author zevtos
 */
public class RemoveFirst extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public RemoveFirst(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("remove_first", "удалить первый элемент из коллекции", tcpServer);
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

            var productToRemove = ticketCollectionManager.getFirst();
            if (productToRemove == null) throw new NotFoundException();

            ticketCollectionManager.remove(productToRemove);
            return new RemoveFirstResponse(true, "Билет успешно удален.");

        } catch (EmptyValueException exception) {
            return new RemoveFirstResponse(false, "Коллекция пуста!");
        } catch (NotFoundException exception) {
            return new RemoveFirstResponse(false, "Билета с таким ID в коллекции нет!");
        }
    }
}
