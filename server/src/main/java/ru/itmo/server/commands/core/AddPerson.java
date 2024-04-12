package ru.itmo.server.commands.core;

import ru.itmo.general.network.requests.AddPersonRequest;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.AddPersonResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.commands.Command;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author zevtos
 */
public class AddPerson extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Add.
     *
     * @param ticketCollectionManager менеджер коллекции
     */
    public AddPerson(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("add_person {element}", "добавить новый объект Person в коллекцию", tcpServer);
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (ожидается отсутствие аргументов)
     * @return Успешность выполнения команды
     */
    @Override
    public Response execute(Request arguments) {
        try {
            var req = (AddPersonRequest) arguments;
            req.getPerson().setId(ticketCollectionManager.getPersonManager().getFreeId());
            if (!req.getPerson().validate()) return new AddPersonResponse(false, "Данные человека не валидны! Личность не добавлена!");
            if(!ticketCollectionManager.getPersonManager().add(req.getPerson())) return new AddPersonResponse(false, "Человек уже существует!");
            return new AddPersonResponse(true, "Человек успешно добавлен!");
        } catch (Exception e){
            return new AddPersonResponse(false, e.toString());
        }
    }
}
