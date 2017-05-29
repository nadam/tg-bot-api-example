package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.reply_markup.ReplyKeyboardRemove;

public class HideKeyboard extends Command {

    private static final ReplyKeyboardRemove REPLY_KEYBOARD_HIDE = new ReplyKeyboardRemove();

    public HideKeyboard(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Hide the custom keyboard";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendMessage(message.chat.id, "Keyboard hidden", null, false, 0, REPLY_KEYBOARD_HIDE);
    }
}