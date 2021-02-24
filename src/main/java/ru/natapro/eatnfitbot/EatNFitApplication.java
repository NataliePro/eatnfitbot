package ru.natapro.eatnfitbot;

import java.util.Map;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.natapro.eatnfitbot.telegram.Bot;

public class EatNFitApplication {

    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        try {
            //System.getProperties().put("proxySet", "true");
            //System.getProperties().put("socksProxyHost", "127.0.0.1");
            //System.getProperties().put("socksProxyPort", "9150");

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
