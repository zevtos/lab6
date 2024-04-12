package ru.itmo.server.commands.custom;

import ru.itmo.general.models.Ticket;
import ru.itmo.general.network.requests.AddIfMinRequest;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.AddIfMinResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'add_if_min'. Добавляет новый элемент в коллекцию, если его цена меньше минимальной.
 *
 * @author zevtos
 */
public class AddIfMin extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды AddIfMin.
     *
     * @param ticketCollectionManager менеджер коллекции
     */
    public AddIfMin(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его цена меньше минимальной цены этой коллекции", tcpServer);
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
            var req = (AddIfMinRequest) arguments;
            var ticket = req.getTicket();

            var minPrice = minPrice();
            if (ticket.getPrice() < minPrice) {
                ticketCollectionManager.add(ticket);
                return new AddIfMinResponse(true, "Билет успешно добавлен!", minPrice);
            } else {
                return new AddIfMinResponse(false, null, minPrice);
            }
        }catch (Exception e){
            return new AddIfMinResponse(false, e.toString(), null);
        }
    }

    private Double minPrice() {
        return ticketCollectionManager.getCollection().stream()
                .map(Ticket::getPrice)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(Double.MAX_VALUE);
    }
}