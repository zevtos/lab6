package ru.itmo.client.commands.custom;

import ru.itmo.client.commands.Command;
import ru.itmo.client.forms.TicketForm;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidFormException;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.InvalidScriptInputException;
import ru.itmo.general.network.requests.AddIfMinRequest;
import ru.itmo.general.network.responses.AddIfMinResponse;

/**
 * Команда 'add_if_min'. Добавляет новый элемент в коллекцию, если его цена меньше минимальной.
 *
 * @author zevtos
 */
public class AddIfMin extends Command {
    private final Console console;
    

    /**
     * Конструктор для создания экземпляра команды AddIfMin.
     *
     * @param console           объект для взаимодействия с консолью
     * @param  tcpClient
     */
    public AddIfMin(Console console, TCPClient tcpClient) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его цена меньше минимальной цены этой коллекции", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @param arguments аргументы команды
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            console.println("* Создание нового билета (add_if_min):");
            var ticket = (new TicketForm(console, tcpClient)).build();

            AddIfMinResponse resp = (AddIfMinResponse) tcpClient.sendCommand(new AddIfMinRequest(ticket));


            if (resp.isSuccess()) {
                console.println("Билет успешно добавлен!");
            } else {
                var minPrice = resp.getMinPrice();
                console.println("Билет не добавлен, цена не минимальная (" + ticket.getPrice() + " > " + minPrice +")");
            }
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля билета не валидны! Продукт не создан!");
        } catch (InvalidScriptInputException ignored) {}
        return false;
    }

}