package ru.itmo.server.commands;

import ru.itmo.general.network.requests.Request;
import ru.itmo.general.network.responses.Response;

/**
 * Интерфейс для всех выполняемых команд.
 *
 * @author zevtos
 */
public interface Executable {
    /**
     * Выполнить команду с заданными аргументами.
     *
     * @param arguments Аргументы команды.
     * @return true, если выполнение команды завершилось успешно, иначе false.
     */
    Response execute(Request arguments);
}
