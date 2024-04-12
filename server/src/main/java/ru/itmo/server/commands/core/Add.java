package ru.itmo.server.commands.core;

import ru.itmo.general.network.requests.AddRequest;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.AddResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.commands.Command;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author zevtos
 */
public class Add extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Add.
     *
     * @param ticketCollectionManager менеджер коллекции
     */
    public Add(TicketCollectionManager ticketCollectionManager, TCPServer tcpServer) {
        super("add {element}", "добавить новый объект Ticket в коллекцию", tcpServer);
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param request
     * @return Успешность выполнения команды
     */
    @Override
    public Response execute(Request request) {
        var req = (AddRequest) request;
        try {
            req.getTicket().setId(ticketCollectionManager.getFreeId());
            req.getTicket().getPerson().setId(ticketCollectionManager.getPersonManager().getFreeId());
            if (!req.getTicket().validate()) {
                return new AddResponse(false, "Билет не добавлен, поля билета не валидны!", -1);
            }
            req.getTicket().setId(ticketCollectionManager.getFreeId());
            if(!ticketCollectionManager.add(req.getTicket())) return new AddResponse(false, "Билет уже существует", -1);
            return new AddResponse(true, null, ticketCollectionManager.getFreeId());
        } catch (Exception e) {
            return new AddResponse(false, e.toString(), -1);
        }
    }
}
