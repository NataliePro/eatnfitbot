package ru.natapro.eatnfitbot.telegram;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class Utils {

    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    /**
     * Формирование имени пользователя. Если заполнен никнейм, используем его. Если нет - используем фамилию и имя
     * @param user пользователь
     */
    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Полученик фамилии пользователя.
     * @param user пользователь
     */
    public static String getUserLastName(User user) {
        return user.getLastName();
    }

    /**
     * Полученик имени пользователя.
     * @param user пользователь
     */
    public static String getUserFirstName(User user) {
        return user.getFirstName();
    }
}
