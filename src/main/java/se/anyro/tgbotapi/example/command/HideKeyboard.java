package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.reply_markup.ReplyKeyboardHide;

public class HideKeyboard extends Command {

    public HideKeyboard(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Hide the custom keyboard";
    }

    @Override
    public void run(Message message) throws IOException {
        ReplyKeyboardHide keyboardHide = new ReplyKeyboardHide();
        api.sendMessage(message.chat.id, "Keyboard hidden", null, false, 0, keyboardHide);
    }
}