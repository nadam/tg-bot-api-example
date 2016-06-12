package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;

public class Reply extends Command {

    public Reply(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Simple reply example";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendReply(message, "Ok");
    }
}
