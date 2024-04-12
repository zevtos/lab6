package ru.itmo.server.commands.core;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.exceptions.NotFoundException;
import ru.itmo.general.network.requests.RemoveRequest;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.RemoveResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по ID.
 * @author zevtos
 */
public class Remove extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Remove.
     *
     * @param ticketCollectionManager менеджер коллекции билетов
     */
    public Remove(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("remove_by_id <ID>", "удалить ticket из коллекции по ID", tcpServer);
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
        try {
            var req = (RemoveRequest) arguments;

            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

            var id = req.getId();
            if(!ticketCollectionManager.remove(id)) throw new NotFoundException();

            return new RemoveResponse(true, "Билет успешно удален.");

        }catch (EmptyValueException exception) {
            return new RemoveResponse(false, "Коллекция пуста!");
        } catch (NotFoundException exception) {
            return new RemoveResponse(false, "Билета с таким ID в коллекции нет!");
        }
    }
}
