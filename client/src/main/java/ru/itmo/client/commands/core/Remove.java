package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.ResponseException;
import ru.itmo.general.network.requests.RemoveRequest;
import ru.itmo.general.network.responses.Response;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по ID.
 * @author zevtos
 */
public class Remove extends Command {
    private final Console console;


    /**
     * Конструктор для создания экземпляра команды Remove.
     *
     * @param console объект для взаимодействия с консолью
     * @param  tcpClient
     */
    public Remove(Console console, TCPClient tcpClient) {
        super("remove_by_id <ID>", "удалить ticket из коллекции по ID", tcpClient);
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
            if (arguments.length < 2 || arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();

            int id = Integer.parseInt(arguments[1]);
            Response resp;
            if (!(resp = tcpClient.sendCommand(new RemoveRequest(id))).isSuccess()) {
                console.printError(resp.getMessage()); throw new ResponseException(resp.getMessage());
            }

            console.println("Билет успешно удален.");
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (ResponseException e) {
            console.printError(e.getMessage());
        }
        return false;
    }
}
