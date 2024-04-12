package ru.itmo.client.commands.custom;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.ResponseException;
import ru.itmo.general.network.requests.MaxByNameRequest;
import ru.itmo.general.network.responses.MaxByNameResponse;

/**
 * Команда 'max_by_name'. Выводит элемент с максимальным именем.
 * @author zevtos
 */
public class MaxByName extends Command {
    private final Console console;
    

    public MaxByName(Console console, TCPClient tcpClient) {
        super("max_by_name", "вывести любой объект из коллекции, значение поля name которого является максимальным", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            MaxByNameResponse resp = (MaxByNameResponse) tcpClient.sendCommand(new MaxByNameRequest());
            if(resp.isSuccess()) {
                console.println(resp.getTicketInfo());
            }else{
                throw new ResponseException(resp.getMessage());
            }
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (ResponseException e) {
            console.printError(e.getMessage());;
        }
        return false;
    }
}
