package ru.natapro.eatnfitbot.telegram.nonCommand;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.natapro.eatnfitbot.telegram.Bot;
import ru.natapro.eatnfitbot.telegram.Utils;
import ru.natapro.eatnfitbot.telegram.domain.Result;
import ru.natapro.eatnfitbot.telegram.domain.User;
import ru.natapro.eatnfitbot.telegram.exceptions.IllegalSettingsException;
import ru.natapro.eatnfitbot.telegram.service.UserService;

/**
 * Обработка сообщения, не являющегося командой (т.е. обычного текста не начинающегося с "/")
 */
public class NonCommand {
    private Logger logger = LoggerFactory.getLogger(NonCommand.class);

    public String nonCommandExecute(Long chatId, Message msg) {
        var text = msg.getText();
        var user = msg.getFrom();
        var userName = Utils.getUserName(user);
        var userFirstName = Utils.getUserFirstName(user);
        var userLastName = Utils.getUserLastName(user);
        logger.debug(String.format("Пользователь %s. Начата обработка сообщения \"%s\", не являющегося командой",
                userName, text));

        Settings settings;
        String answer;
        try {
            logger.debug(String.format("Пользователь %s. Пробуем создать объект настроек из сообщения \"%s\"",
                    userName, text));
            settings = createSettings(text);
            saveUserSettings(chatId, settings, userName, userFirstName, userLastName);
            logger.debug(String.format("Пользователь %s. Объект настроек из сообщения \"%s\" создан и сохранён",
                    userName, text));
            answer = "Спасибо! Давайте начнем! Я буду ежедневно кидать вам ссылки в обиен на вес!";
        } catch (IllegalSettingsException e) {
            logger.debug(String.format("Пользователь %s. Не удалось создать объект настроек из сообщения \"%s\". " +
                    "%s", userName, text, e.getMessage()));
            answer = e.getMessage() +
                    "\n\n❗ Вы неверно ввели начальные значения!";
        } catch (Exception e) {
            logger.debug(String.format("Пользователь %s. Не удалось создать объект настроек из сообщения \"%s\". " +
                    "%s. %s", userName, text, e.getClass().getSimpleName(), e.getMessage()));
            answer = "Простите, я не понимаю Вас. Похоже, что Вы ввели сообщение, не соответствующее формату, или " +
                    "использовали слишком большие числа\n\n" +
                    "Возможно, Вам поможет /help";
        }

        logger.debug(String.format("Пользователь %s. Завершена обработка сообщения \"%s\", не являющегося командой",
                    userName, text));
        return answer;
    }

    /**
     * Создание настроек из полученного пользователем сообщения
     * @param text текст сообщения
     * @throws IllegalArgumentException пробрасывается, если сообщение пользователя не соответствует формату
     */
    private Settings createSettings(String text) throws IllegalArgumentException {
        //отсекаем файлы, стикеры, гифки и прочий мусор
        if (text == null) {
            throw new IllegalArgumentException("Сообщение не является текстом");
        }
        text = text.replaceAll("-", "")//избавляемся от отрицательных чисел
                .replaceAll(" ", "@");//меняем разделитель-пробел на @
        String[] parameters = text.split("@");
        if (parameters.length != 3) {
            throw new IllegalArgumentException(String.format("Не удалось разбить сообщение \"%s\" на 3 составляющих",
                    text));
        }
        int age = Integer.parseInt(parameters[0]);
        Double startWeight = Double.parseDouble(parameters[1]);
        String phone = parameters[2];

        validateSettings(age, startWeight, phone);
        return new Settings(startWeight, age, phone);
    }

    /**
     * Валидация настроек
     */
    private void validateSettings(int age, Double startWeight, String phone) {
        if (age == 0) {
            throw new IllegalSettingsException("\uD83D\uDCA9 Возраст не может равняться 0");
        }

        if (startWeight == 0.0) {
            throw new IllegalSettingsException("\uD83D\uDCA9 Вес не может равняться 0");
        }
    }

    /**
     * Сохранение настроек пользователя в базу данных
     * @param chatId id чата
     * @param settings настройки
     */
    private void saveUserSettings(Long chatId, Settings settings, String userName, String userFirstName,
        String userLastName) {

        var userService = new UserService();
        User user = userService.findUser(chatId);
        boolean isNewUser = false;
        if (user == null) {
            user = new User();
            isNewUser = true;
        }

        user.setChatId(chatId);
        user.setStartWeight(settings.getStartWeight());
        user.setAge(settings.getAge());
        user.setPhone(settings.getPhone());
        user.setNickName(userName);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
        if (isNewUser) {
            userService.saveUser(user);
        } else {
            userService.updateUser(user);
        }

        Result result = new Result();
        result.setDate(LocalDate.now());
        result.setUser(user);
        result.setCurrentWeight(settings.getStartWeight());
        userService.saveUserResult(result);
    }
}