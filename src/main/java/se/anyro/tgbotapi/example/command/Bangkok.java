package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;

/**
 * Demonstrating the use of sendLocation()
 */
public class Bangkok extends Command {

    public Bangkok(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Show location of Bangkok";
    }

    @Override
    public void run(Message message) throws IOException {
        long chatId = message.chat.id;
        api.sendLocation(chatId, 13.752725f, 100.530195f, 0, 0, null);
    }
}