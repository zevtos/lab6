package ru.itmo.client.commands.core;

import ru.itmo.client.commands.Command;
import ru.itmo.client.forms.PersonForm;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.general.exceptions.InvalidFormException;
import ru.itmo.general.exceptions.InvalidNumberOfElementsException;
import ru.itmo.general.exceptions.InvalidScriptInputException;
import ru.itmo.general.network.requests.AddPersonRequest;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author zevtos
 */
public class AddPerson extends Command {
    private final Console console;

    /**
     * Конструктор для создания экземпляра команды Add.
     *
     * @param console              объект для взаимодействия с консолью
     */
    public AddPerson(Console console, TCPClient tcpClient) {
        super("add_person {element}", "добавить новый объект Person в коллекцию", tcpClient);
        this.console = console;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (ожидается отсутствие аргументов)
     * @return Успешность выполнения команды
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments.length > 1 && !arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            console.println("* Создание нового пользователя:");
            var personForm = new PersonForm(console, tcpClient);
            var person = personForm.build();
            if(tcpClient.sendCommand(new AddPersonRequest(person)).isSuccess()) {
                console.println("Пользователь успешно добавлен!");
                return true;
            } else {
                console.println("Пользователь с таким PassportID уже существует!");
                return false;
            }
        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля пользователя не валидны! Пользователь не создан!");
        } catch (InvalidScriptInputException ignored) {
            // Ignored
        }
        return false;
    }
}
