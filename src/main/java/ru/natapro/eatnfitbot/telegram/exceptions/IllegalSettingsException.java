package ru.natapro.eatnfitbot.telegram.exceptions;

/**
 * Исключение, пробрасываемое в случае получения невалидных начальных значений
 */
public class IllegalSettingsException extends IllegalArgumentException {

    public IllegalSettingsException(String s) {
        super(s);
    }
}