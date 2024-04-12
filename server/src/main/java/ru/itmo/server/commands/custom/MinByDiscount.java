package ru.itmo.server.commands.custom;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.models.Ticket;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.MinByDiscountResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'min_by_discount'. выводит элемент с минимальным discount.
 * @author zevtos
 */
public class MinByDiscount extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public MinByDiscount(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("min_by_discount", "вывести любой объект из коллекции, значение поля discount которого является минимальным", tcpServer);
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

            Ticket minTicketByDiscount = minByDiscount();
            return new MinByDiscountResponse(true, null, minTicketByDiscount.toString());

        } catch (EmptyValueException exception) {
            return new MinByDiscountResponse(false, "Коллекция пуста!", null);
        }
    }

    private Ticket minByDiscount(){
        long minDiscount = 101;
        int ticketId = -1;
        for (Ticket c : ticketCollectionManager.getCollection()) {
            if (c.getDiscount() != null && c.getDiscount() < minDiscount) {
                minDiscount = c.getDiscount();
                ticketId = c.getId();
            }
        }
        if(ticketId == -1) return ticketCollectionManager.getFirst();
        return ticketCollectionManager.byId(ticketId);
    }
}
