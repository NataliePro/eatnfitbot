package ru.natapro.eatnfitbot.telegram.commands.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.natapro.eatnfitbot.telegram.Bot;
import ru.natapro.eatnfitbot.telegram.Utils;
import ru.natapro.eatnfitbot.telegram.nonCommand.Settings;

/**
 * Команда получения текущих настроек
 */
public class SettingsCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(SettingsCommand.class);

    public SettingsCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        Long chatId = chat.getId();
        Settings settings = Bot.getUserSettings(chatId);
        sendAnswer(absSender, chatId, this.getCommandIdentifier(), userName,
                String.format("*Проверьте ваши данные*\n" +
                                "- возраст - *%s*\n" +
                                "- вес - *%s*\n" +
                                "- номер телефона - *%s*\n\n" +
                                "Если Вы хотите изменить эти параметры, введите заново через пробел 3 значения - " +
                                "возраст, вес и нмер телефона \n\n" +
                                "\uD83D\uDC49 Например, 60 86 89145647588",
                        settings.getAge(), settings.getStartWeight(), settings.getPhone()));

        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}