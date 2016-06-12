package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.reply_markup.KeyboardButton;
import se.anyro.tgbotapi.types.reply_markup.ReplyKeyboardMarkup;

/**
 * Custom keyboard for text, contact and location.
 */
public class Keyboard extends Command {

    private static final ReplyKeyboardMarkup MARKUP = ReplyKeyboardMarkup.createVertical(
            KeyboardButton.textButton("/help"),
            KeyboardButton.contactButton("Share contact"),
            KeyboardButton.locationButton("Share location"),
            KeyboardButton.textButton("/hidekeyboard")
            );

    public Keyboard(TgBotApi api) {
        super(api);
        MARKUP.one_time_keyboard = true;
    }

    @Override
    public String getDescription() {
        return "Display a custom keyboard";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendMessage(message.chat.id, "Please select a command", null, false, 0, MARKUP);
    }
}