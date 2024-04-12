package ru.itmo.client.commands.custom;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.ResponseException;
import ru.itmo.general.network.requests.RemoveFirstRequest;
import ru.itmo.general.network.responses.RemoveFirstResponse;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 * @author zevtos
 */
public class RemoveFirst extends Command {
    private final Console console;
    

    public RemoveFirst(Console console, TCPClient tcpClient) {
        super("remove_first", "удалить первый элемент из коллекции", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            RemoveFirstResponse resp = (RemoveFirstResponse) tcpClient.sendCommand(new RemoveFirstRequest());
            if(resp.isSuccess()) {
                console.println("Билет успешно удален.");
            }else throw new ResponseException(resp.getMessage());
            return true;

        } catch (ResponseException exception) {
            console.printError(exception.getMessage());
        }
        return false;
    }
}
