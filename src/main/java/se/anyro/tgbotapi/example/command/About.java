package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.ParseMode;

public class About extends Command {

    private final String aboutText;

    public About(TgBotApi api) {
        super(api);
        aboutText = '*' + api.getBotName() + '*' + " is an example bot showing how to use TgBotApi";

    }

    @Override
    public String getDescription() {
        return "Information about this bot";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendMessage(message.chat.id, aboutText, ParseMode.MARKDOWN, true, 0, null);
    }
}