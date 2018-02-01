package se.anyro.tgbotapi.example;

import static se.anyro.tgbotapi.example.BuildVars.OWNER;
import static se.anyro.tgbotapi.example.BuildVars.TOKEN;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.TgBotApi.ErrorListener;
import se.anyro.tgbotapi.example.command.About;
import se.anyro.tgbotapi.example.command.Bangkok;
import se.anyro.tgbotapi.example.command.Command;
import se.anyro.tgbotapi.example.command.DummyGame;
import se.anyro.tgbotapi.example.command.Forward;
import se.anyro.tgbotapi.example.command.Help;
import se.anyro.tgbotapi.example.command.HideKeyboard;
import se.anyro.tgbotapi.example.command.InlineKeyboard;
import se.anyro.tgbotapi.example.command.Keyboard;
import se.anyro.tgbotapi.example.command.Markdown;
import se.anyro.tgbotapi.example.command.Profile;
import se.anyro.tgbotapi.example.command.Reply;
import se.anyro.tgbotapi.example.command.ToDoc;
import se.anyro.tgbotapi.example.inline.InlineQueryHandler;
import se.anyro.tgbotapi.types.Location;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.Update;

/**
 * Main servlet class receiving messages and processing the commands.
 */
@SuppressWarnings("serial")
public class ExampleServlet extends HttpServlet implements ErrorListener {

    private TgBotApi api;
    
    private Map<String, Command> commands = new LinkedHashMap<>();

    private InlineKeyboard inlineKeyboard;
    private DummyGame game;

    private InlineQueryHandler inlineQueryHandler;
    
    public ExampleServlet() {
        super();
        api = new TgBotApi(TOKEN, OWNER, this);

        // Setup commands
        addCommand(new Help(api, commands.values()));
        addCommand(new Profile(api));
        addCommand(new Reply(api));
        addCommand(new Forward(api));
        addCommand(new ToDoc(api));
        addCommand(new Markdown(api));
        addCommand(new Keyboard(api));
        inlineKeyboard = new InlineKeyboard(api);
        addCommand(inlineKeyboard);
        game = new DummyGame(api);
        addCommand(game);
        addCommand(new HideKeyboard(api));
        addCommand(new Bangkok(api));
        addCommand(new About(api));
        inlineQueryHandler = new InlineQueryHandler(api);

        api.debug("Bot started");
    }

    private void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setStatus(HttpServletResponse.SC_OK);

        Update update = api.parseFromWebhook(req.getReader());
        try {
            if (update.isMessage()) {
                handleMessage(update.message);
            } else if (update.isInlineQuery()) {
                inlineQueryHandler.handleInlineQuery(update.inline_query);
            } else if (update.isChosenInlineResult()) {
                // Not used at the moment
                // inlineQueryHandler.handleChosenInlineResult(update.chosen_inline_result);
            } else if (update.isCallbackQuery()) {
                if (update.callback_query.game_short_name != null) {
                    game.handleCallbackQuery(update.callback_query);
                } else {
                    inlineKeyboard.handleCallbackQuery(update.callback_query);
                }
            } else {
                api.debug("Unexpected update");
            }
        } catch (Exception e) {
            // Simplify testing for the owner of the bot
            if (update.fromUserId() == OWNER) {
                api.debug(e);
            }
        }
    }

    private void handleMessage(Message message) throws IOException {
        String text = message.text;
        if (text == null) {
            // Allow commands in captions too for photos etc.
            text = message.caption;
        }

        // Find command and run it
        if (text != null && text.startsWith("/")) {
            String[] parts = text.substring(1).split(" ", 2);
            Command command = commands.get(parts[0]);
            if (command != null) {
                command.run(message);
                return;
            }
        }

        if (message.contact != null) {
            api.sendMessage(message.chat.id, message.contact.phone_number);
            return;
        }
        
        Location location = message.location;
        if (location != null) {
            api.sendLocation(message.chat.id, location.latitude, location.longitude, 0, null);
            return;
        }

        if (message.document != null) {
            api.sendMessage(message.chat.id, "File id: " + message.document.file_id);
            return;
        }
        
        // Unknown command. Show help text instead
        commands.get("help").run(message);
    }

    @Override
    public void onError(int errorCode, String description) {
        api.debug(new Exception("ErrorCode " + errorCode + ", " + description));
    }
}