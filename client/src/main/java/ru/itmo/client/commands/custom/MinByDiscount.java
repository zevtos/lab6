package ru.itmo.client.commands.custom;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.ResponseException;
import ru.itmo.general.network.requests.MinByDiscountRequest;
import ru.itmo.general.network.responses.MinByDiscountResponse;

/**
 * Команда 'min_by_discount'. выводит элемент с минимальным discount.
 * @author zevtos
 */
public class MinByDiscount extends Command {
    private final Console console;
    

    public MinByDiscount(Console console, TCPClient tcpClient) {
        super("min_by_discount", "вывести любой объект из коллекции, значение поля discount которого является минимальным", tcpClient);
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

            MinByDiscountResponse resp = (MinByDiscountResponse) tcpClient.sendCommand(new MinByDiscountRequest());
            if(resp.isSuccess()){
                console.println(resp.getTicketInfo());
            }else throw new ResponseException(resp.getMessage());
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        }catch (ResponseException exception) {
            console.printError(exception.getMessage());
        }
        return false;
    }
}
