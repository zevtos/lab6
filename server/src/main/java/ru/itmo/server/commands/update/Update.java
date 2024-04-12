package ru.itmo.server.commands.update;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.exceptions.NotFoundException;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.requests.UpdateRequest;
import ru.itmo.general.network.responses.Response;
import ru.itmo.general.network.responses.UpdateResponse;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 * @author zevtos
 */
public class Update extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public Update(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID", tcpServer);
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     *
     * @param arguments Аргументы команды.
     * @return Успешность выполнения команды.
     */
    @Override
    public Response execute(Request arguments) {
        try {
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

            var req = (UpdateRequest) arguments;
            var id = req.getId();
            var ticket = ticketCollectionManager.byId(id);
            if (ticket == null) throw new NotFoundException();

            var newTicket = req.getTicket();
            ticket.update(newTicket);

            return new UpdateResponse(true, "Билет успешно обновлен.");

        } catch (EmptyValueException exception) {
            return new UpdateResponse(false, "Коллекция пуста!");
        } catch (NotFoundException exception) {
            return new UpdateResponse(false, "Билета с таким ID в коллекции нет!");
        }
    }
}
