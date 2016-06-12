package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;

public class Forward extends Command {

    public Forward(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Simple forward example";
    }

    @Override
    public void run(Message message) throws IOException {
        api.forwardMessage(message.chat.id, message);
    }
}
