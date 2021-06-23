package com.gmail.petrikov05.app.bot.config;

import com.gmail.petrikov05.app.bot.BotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Profile("!test")
public class TravelBotInitialization extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String botToken;
    private final BotService botService;

    public TravelBotInitialization(BotService botService) {this.botService = botService;}

    @Override
    public void onUpdateReceived(Update update) {
        if (isHasMessage(update)) {
            String text = update.getMessage().getText().trim();
            String message = botService.getMessage(text);
            try {
                execute(new SendMessage().setChatId(update.getMessage().getChatId())
                        .setText(message));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private boolean isHasMessage(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText();
    }

}
