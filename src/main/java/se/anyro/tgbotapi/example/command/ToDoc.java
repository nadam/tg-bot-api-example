package se.anyro.tgbotapi.example.command;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.ChatAction;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.file.PhotoSize;

/**
 * Useless command for testing/demonstrating how to download a file and sending it again.
 */
public class ToDoc extends Command {

    public ToDoc(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "Convert photo to doc";
    }

    @Override
    public void run(Message message) throws IOException {
        long chatId = message.chat.id;
        if (message.photo == null) {
            api.sendMessage(chatId, " \"/" + getName() + "\" should only be sent as caption to an image");
            return;
        }
        PhotoSize photoSize = message.photo[0];
        for (PhotoSize size : message.photo) {
            if (size.file_size < photoSize.file_size) {
                photoSize = size;
            }
        }
        api.sendChatAction(chatId, ChatAction.UPLOAD_DOCUMENT);
        byte[] photo = api.downloadFile(photoSize.file_id);
        ByteArrayInputStream photoStream = new ByteArrayInputStream(photo);
        api.sendDocument(chatId, photoStream, "photo", "Photo as document", 0, null);
    }
}