package ru.itmo.client.utility;

import java.util.Scanner;

/**
 * Отвечает за режим ввода пользовательских данных
 * @author zevtos
 */
public class Interrogator {

    private static Scanner userScanner;
    private static boolean fileMode = false;

    /**
     * Возвращает сканер для пользовательского ввода.
     * @return Сканер для пользовательского ввода.
     */
    public static Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Устанавливает сканер для пользовательского ввода.
     * @param userScanner Сканер для пользовательского ввода.
     */
    public static void setUserScanner(Scanner userScanner) {
        Interrogator.userScanner = userScanner;
    }

    /**
     * Проверяет, находится ли программа в режиме ввода из файла.
     * @return true, если программа находится в режиме ввода из файла, иначе false.
     */
    public static boolean fileMode() {
        return fileMode;
    }

    /**
     * Устанавливает режим пользовательского ввода.
     */
    public static void setUserMode() {
        Interrogator.fileMode = false;
    }

    /**
     * Устанавливает режим ввода из файла.
     */
    public static void setFileMode() {
        Interrogator.fileMode = true;
    }
}
