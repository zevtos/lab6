package ru.itmo.client.utility.runtime;


import ru.itmo.client.commands.core.*;
import ru.itmo.client.commands.custom.*;
import ru.itmo.client.commands.special.SumOfPrice;
import ru.itmo.client.commands.update.Update;
import ru.itmo.client.managers.CommandManager;
import ru.itmo.client.network.TCPClient;
import ru.itmo.client.utility.console.Console;
import ru.itmo.client.utility.console.StandartConsole;
import sun.misc.Signal;

/**
 * Запускает выполнение программы.
 * @author zevtos
 */
public class Runner {
    Console console;
    CommandManager commandManager;
    InteractiveRunner interactiveRunner;
    ScriptRunner scriptRunner;

    /**
     * Конструктор для Runner.
     * @param console Консоль.
     * @param commandManager Менеджер команд.
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.scriptRunner = new ScriptRunner(console, commandManager);
        this.interactiveRunner = new InteractiveRunner(console, commandManager, scriptRunner);
    }

    public Runner(Console console, TCPClient client) {
        this.console = console;
        this.commandManager = this.createCommandManager(client);
        this.scriptRunner = new ScriptRunner(console, commandManager);
        this.interactiveRunner = new InteractiveRunner(console, commandManager, scriptRunner);
    }

    /**
     * Запускает интерактивный режим выполнения программы.
     */
    public void run() {
        // обработка сигналов
        setSignalProcessing('\n' + "Для получения справки введите 'help', для завершения программы введите 'exit'" + '\n' + console.getPrompt(),
                "INT", "TERM", "TSTP", "BREAK", "EOF");

        interactiveRunner.run("");
    }

    /**
     * Запускает выполнение скрипта.
     * @param argument Аргумент - путь к файлу скрипта.
     */
    public void run_script(String argument){
        scriptRunner.run(argument);
    }

    /**
     * Коды завершения выполнения программы.
     */
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }
    /**
     * Создает менеджер команд приложения.
     *
     * @return менеджер команд
     */
    private CommandManager createCommandManager(TCPClient client) {
        return new CommandManager() {{
            register("help", new Help(console, this, client));
            register("info", new Info(console, client));
            register("show", new Show(console, client));
            register("add", new Add(console, client));
            register("update", new Update(console, client));
            register("remove_by_id", new Remove(console, client));
            register("clear", new Clear(console, client));
            register("execute_script", new ExecuteScript(console, client));
            register("exit", new Exit(console, client));
            register("remove_first", new RemoveFirst(console, client));
            register("remove_head", new RemoveHead(console, client));
            register("add_if_min", new AddIfMin(console, client));
            register("sum_of_price", new SumOfPrice(console, client));
            register("min_by_discount", new MinByDiscount(console, client));
            register("max_by_name", new MaxByName(console, client));
            register("history", new History(console, this, client));
            register("add_person", new AddPerson(console, client));
        }};
    }
    private static void setSignalProcessing(String messageString, String... signalNames) {
        for (String signalName : signalNames) {
            try {
                Signal.handle(new Signal(signalName), signal -> System.out.print(messageString));
            } catch (IllegalArgumentException ignored) {
                // Игнорируем исключение, если сигнал с таким названием уже существует или такого сигнала не существует
            }
        }
    }
}
