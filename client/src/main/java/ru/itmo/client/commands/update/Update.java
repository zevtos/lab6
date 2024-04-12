package ru.itmo.client.commands.update;

import ru.itmo.client.commands.Command;
import ru.itmo.client.forms.TicketForm;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.*;
import ru.itmo.general.network.requests.UpdateRequest;
import ru.itmo.general.network.responses.UpdateResponse;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 * @author zevtos
 */
public class Update extends Command {
    private final Console console;
    

    public Update(Console console, TCPClient tcpClient) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду
     *
     * @param arguments Аргументы команды.
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();

            var id = Integer.parseInt(arguments[1]);

            console.println("* Введите данные обновленного билета:");
            console.prompt();

            var newTicket = (new TicketForm(console, tcpClient)).build();
            UpdateResponse resp = (UpdateResponse) tcpClient.sendCommand(new UpdateRequest(id, newTicket));
            if(!resp.isSuccess()) throw new ResponseException(resp.getMessage());

            console.println("Билет успешно обновлен.");
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (ResponseException exception) {
            console.printError(exception.getMessage());
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (InvalidScriptInputException e) {
            console.printError("Некорректный ввод в скрипте!");
        } catch (InvalidFormException e) {
            console.printError("Поля билета не валидны! Билет не обновлен!");
        }
        return false;
    }
}
