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
public class InlineKeyboard extends Command {

    private enum CallbackData { EDIT, ANSWER, ALERT, HIDE };
    
    private InlineKeyboardMarkup markup = InlineKeyboardMarkup.createVertical(
            callbackDataButton("Edit message", CallbackData.EDIT),
            callbackDataButton("Send answer", CallbackData.ANSWER),
            callbackDataButton("Alert", CallbackData.ALERT),
            InlineKeyboardButton.url("URL", "http://google.com"),
            InlineKeyboardButton.switchInlineQuery("Switch to inline", "Text from inline keyboard"),
            callbackDataButton("Hide keyboard", CallbackData.HIDE)
            );
    
    private InlineKeyboardButton callbackDataButton(String text, CallbackData cb) {
        return InlineKeyboardButton.callbackData(text, cb.toString());
    }
    
    public InlineKeyboard(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Display an inline keyboard";
    }

    @Override
    public void run(Message message) throws IOException {
        api.sendMessage(message.chat.id, "Message with inline keyboard", null, false, 0, markup);
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        try {
            String data = callbackQuery.data;
            Message message = callbackQuery.message;

            CallbackData command = CallbackData.valueOf(data);

            switch (command) {
            case EDIT:
                if (message.text.length() > 42) {
                    return;
                }
                String newText = message.text + '!';
                api.editMessageText(message.chat.id, message.message_id, newText, null, true, markup);
                break;
            case ANSWER:
                api.answerCallbackQuery(callbackQuery, "This is a normal answer", false);
                break;
            case ALERT:
                api.answerCallbackQuery(callbackQuery, "This is an alert", true);
                break;
            case HIDE:
                api.editMessageText(message.chat.id, message.message_id, "Message without inline keyboard", null, true,
                        null); // No markup
                break;
            default:
                api.debug("Unknown command in callbackQuery: " + data);
            }
        } catch (IOException e) {
            if (api.isOwner(callbackQuery.from)) {
                api.debug(e);
            }
        }
    }
}