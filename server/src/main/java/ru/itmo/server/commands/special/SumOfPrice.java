package ru.itmo.server.commands.special;

import ru.itmo.general.exceptions.EmptyValueException;
import ru.itmo.general.models.Ticket;
import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.Response;
import ru.itmo.general.network.responses.SumOfPriceResponse;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'sum_of_price'. Сумма цен всех билетов.
 * @author zevtos
 */
public class SumOfPrice extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    public SumOfPrice(TicketCollectionManager ticketCollectionManager, ru.itmo.server.network.TCPServer tcpServer) {
        super("sum_of_price", "вывести сумму значений поля price для всех элементов коллекции", tcpServer);
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
            var sumOfPrice = getSumOfPrice();

            return new SumOfPriceResponse(true, null, sumOfPrice);

        } catch (EmptyValueException exception) {
            return new SumOfPriceResponse(false, "Коллекция пуста!", 0);
        }
    }

    private Double getSumOfPrice() {
        return ticketCollectionManager.getCollection().stream()
                .map(Ticket::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
