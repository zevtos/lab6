package ru.itmo.general.network.responses;

import ru.itmo.general.utility.CommandName;

import java.io.Serializable;

public abstract class Response implements Serializable {
    private final boolean success; // Успешно ли выполнена команда
    private final String message; // Сообщение об успешном или неудачном выполнении команды
    private final Object data; // Данные, которые может вернуть сервер в ответ на команду

    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
