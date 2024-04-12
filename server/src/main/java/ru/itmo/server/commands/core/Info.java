package ru.itmo.server.commands.core;

import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.InfoResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.commands.Command;
import ru.itmo.server.network.TCPServer;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 *
 * @author zevtos
 */
public class Info extends Command {
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Info.
     *
     * @param ticketCollectionManager менеджер коллекции билетов
     */
    public Info(TicketCollectionManager ticketCollectionManager, TCPServer tcpServer) {
        super("info", "вывести информацию о коллекции", tcpServer);
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

        LocalDateTime ticketLastSaveTime = ticketCollectionManager.getLastSaveTime();
        String ticketLastSaveTimeString = (ticketLastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                ticketLastSaveTime.toLocalDate().toString() + " " + ticketLastSaveTime.toLocalTime().toString();
        LocalDateTime personLastSaveTime = ticketCollectionManager.getPersonManager().getLastSaveTime();
        String personLastSaveTimeString = (personLastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                personLastSaveTime.toLocalDate().toString() + " " + personLastSaveTime.toLocalTime().toString();

        String message;

        message = "Сведения о коллекции:" +'\n' +
                " Тип: " + ticketCollectionManager.collectionType() + '\n' +
                " Количество элементов Ticket: " + ticketCollectionManager.collectionSize() +'\n' +
                " Количество элементов Person: " + ticketCollectionManager.getPersonManager().collectionSize() +'\n' +
                " Дата последнего сохранения:" +'\n' +
                "\tTickets: " + ticketLastSaveTimeString +'\n' +
                "\tPersons: " + personLastSaveTimeString;

        return new InfoResponse(true, null, message);
    }
}
