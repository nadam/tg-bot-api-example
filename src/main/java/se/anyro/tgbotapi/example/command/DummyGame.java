package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.inline.CallbackQuery;
import se.anyro.tgbotapi.types.reply_markup.InlineKeyboardButton;
import se.anyro.tgbotapi.types.reply_markup.InlineKeyboardMarkup;

/**
 * Example of using inline keyboard, external links, switch to inline, alerts and editing messages.
 */
public class DummyGame extends Command {

    private InlineKeyboardMarkup markup = InlineKeyboardMarkup.createVertical(
            InlineKeyboardButton.callbackGame("Play Dummy")
            );

    public DummyGame(TgBotApi api) {
        super(api);
    }

    @Override
    public String getName() {
        return "game";
    }
    
    @Override
    public String getDescription() {
        return "Dummy game";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendGame(message.chat.id, "dummy", 0, markup);
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        try {
            api.answerCallbackQuery(callbackQuery, null, false, "https://github.com/nadam/tg-bot-api-example", 0);
        } catch (IOException e) {
            api.debug(e);
        }
    }
}