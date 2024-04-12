package ru.itmo.server.managers;



import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.CommandNotFoundResponse;
import ru.itmo.general.network.responses.Response;
import ru.itmo.server.commands.Command;
import ru.itmo.server.commands.core.*;
import ru.itmo.server.commands.custom.*;
import ru.itmo.server.commands.special.SumOfPrice;
import ru.itmo.server.commands.update.Update;
import ru.itmo.server.managers.collections.TicketCollectionManager;
import ru.itmo.server.network.TCPServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Управляет командами.
 *
 * @author zevtos
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> commandHistory = new ArrayList<>();

    public CommandManager(TicketCollectionManager ticketCollectionManager, TCPServer tcpServer){
        register("info", new Info(ticketCollectionManager, tcpServer));
        register("show", new Show(ticketCollectionManager, tcpServer));
        register("add", new Add(ticketCollectionManager, tcpServer));
        register("update", new Update(ticketCollectionManager, tcpServer));
        register("remove_by_id", new Remove(ticketCollectionManager, tcpServer));
        register("clear", new Clear(ticketCollectionManager, tcpServer));
        register("exit", new Exit(ticketCollectionManager, tcpServer));
        register("remove_first", new RemoveFirst(ticketCollectionManager, tcpServer));
        register("remove_head", new RemoveHead(ticketCollectionManager, tcpServer));
        register("add_if_min", new AddIfMin(ticketCollectionManager, tcpServer));
        register("sum_of_price", new SumOfPrice(ticketCollectionManager, tcpServer));
        register("min_by_discount", new MinByDiscount(ticketCollectionManager, tcpServer));
        register("max_by_name", new MaxByName(ticketCollectionManager, tcpServer));
        register("add_person", new AddPerson(ticketCollectionManager, tcpServer));
    }

    /**
     * Регистрирует команду.
     *
     * @param commandName Название команды.
     * @param command     Команда.
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * Получает словарь команд.
     *
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Получает историю команд.
     *
     * @return История команд.
     */
    public List<String> getCommandHistory() {
        return commandHistory;
    }

    /**
     * Добавляет команду в историю.
     *
     * @param command Команда.
     */
    public void addToHistory(String command) {
        commandHistory.add(command);
    }
    public Response handle(Request request) {
        var command = this.getCommands().get(request.getCommand());
        if (command == null) return new CommandNotFoundResponse(request.getCommand());
        return command.execute(request);
    }
}
