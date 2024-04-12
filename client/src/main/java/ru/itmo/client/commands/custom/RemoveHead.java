package ru.itmo.client.commands.custom;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.ResponseException;
import ru.itmo.general.network.requests.RemoveHeadRequest;
import ru.itmo.general.network.responses.RemoveHeadResponse;

/**
 * Команда 'remove_head'. Выводит первый элемент коллекции и удаляет его.
 * @author zevtos
 */
public class RemoveHead extends Command {
    private final Console console;
    

    public RemoveHead(Console console, TCPClient tcpClient) {
        super("remove_head", "вывести первый элемент коллекции и удалить его", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            RemoveHeadResponse resp = (RemoveHeadResponse) tcpClient.sendCommand(new RemoveHeadRequest());
            if(resp.isSuccess()) {
                console.println("Билет успешно удален.");
            }else throw new ResponseException(resp.getMessage());
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (ResponseException e){
            console.printError(e.getMessage());
        }
        return false;
    }
}
