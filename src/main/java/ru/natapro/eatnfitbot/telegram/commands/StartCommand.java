package ru.natapro.eatnfitbot.telegram.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.natapro.eatnfitbot.telegram.Utils;

/**
 * Команда "Старт"
 */
public class StartCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        logger.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Давайте начнём! Нам необходимо немного информации о Вас, введите 3 значения через пробел:"
                    + "Ваш возраст (число), Ваш вес (число), Ваш номер телефона."
                    + "Если Вам нужна помощь, нажмите /help");
        logger.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}