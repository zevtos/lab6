package ru.itmo.server.commands.core;

import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.ExitResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.commands.Command;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.network.TCPServer;

/**
 * Команда 'exit'. Завершает выполнение программы (без сохранения в файл).
 * @author zevtos
 */
public class Exit extends Command {
    private TicketCollectionManager ticketCollectionManager;
    /**
     * Конструктор для создания экземпляра команды Exit.
     *
     */
    public Exit(TicketCollectionManager ticketCollectionManager, TCPServer tcpServer) {
        super("exit", "завершить программу (без сохранения в файл)", tcpServer);
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
            ticketCollectionManager.saveCollection();
            return new ExitResponse(true, null);
        } catch (Exception e){
            return new ExitResponse(false, e.toString());
        }
    }
}
