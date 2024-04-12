package ru.itmo.client.commands.special;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.ResponseException;
import ru.itmo.general.network.requests.SumOfPriceRequest;
import ru.itmo.general.network.responses.SumOfPriceResponse;

/**
 * Команда 'sum_of_price'. Сумма цен всех билетов.
 * @author zevtos
 */
public class SumOfPrice extends Command {
    private final Console console;
    

    public SumOfPrice(Console console, TCPClient tcpClient) {
        super("sum_of_price", "вывести сумму значений поля price для всех элементов коллекции", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            SumOfPriceResponse resp = (SumOfPriceResponse) tcpClient.sendCommand(new SumOfPriceRequest());
            if(resp.isSuccess()) {
                var sumOfPrice = resp.getSum();
                console.println("Сумма цен всех билетов: " + sumOfPrice);
            }else throw new ResponseException(resp.getMessage());

            return true;
        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (ResponseException exception) {
            console.println(exception.getMessage());
        }
        return false;
    }
}
