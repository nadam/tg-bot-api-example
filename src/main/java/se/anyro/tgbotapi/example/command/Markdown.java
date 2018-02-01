package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.ParseMode;

public class Markdown extends Command {

    public Markdown(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Replies with the same text using markdown";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendMessage(message.chat.id, message.text.substring(getName().length() + 2), ParseMode.MARKDOWN, false, 0,
                null);
    }
}
