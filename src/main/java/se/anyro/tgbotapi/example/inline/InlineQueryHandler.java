package se.anyro.tgbotapi.example.inline;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.User;
import se.anyro.tgbotapi.types.inline.ChosenInlineResult;
import se.anyro.tgbotapi.types.inline.InlineQuery;
import se.anyro.tgbotapi.types.inline.InlineQueryResult;
import se.anyro.tgbotapi.types.inline.InlineQueryResultArticle;

public class InlineQueryHandler {

    private TgBotApi api;

    public InlineQueryHandler(TgBotApi api) {
        this.api = api;
    }

    /**
     * Called several times while the user is using the bot inline.
     */
    public void handleInlineQuery(InlineQuery inlineQuery) throws IOException {
        User user = inlineQuery.from;
        StringBuilder builder = new StringBuilder();
        if (inlineQuery.query != null) {
            builder.append("Query text: ").append(inlineQuery.query).append('\n');
        }
        if (user.username != null) {
            builder.append("@").append(user.username).append('\n');
        }
        builder.append("Id: ").append(user.id).append('\n');
        builder.append("First: ").append(user.first_name).append('\n');
        if (user.last_name != null) {
            builder.append("Last: ").append(user.last_name).append('\n');
        }
        String message = builder.toString();
        InlineQueryResultArticle article = new InlineQueryResultArticle("0", "Your user information", message);
        article.description = "Name, username, id, etc...";
        InlineQueryResult[] results = { article };
        api.answerInlineQuery(inlineQuery.id, results, true);
    }

    /**
     * Called after the user has selected the message.
     */
    public void handleChosenInlineResult(ChosenInlineResult chosenInlineResult) throws IOException {
        api.sendMessage(chosenInlineResult.from.id, "Good choice!");
    }
}