package se.anyro.tgbotapi.example.command;

import java.io.IOException;

import se.anyro.tgbotapi.TgBotApi;
import se.anyro.tgbotapi.types.Message;
import se.anyro.tgbotapi.types.User;
import se.anyro.tgbotapi.types.file.PhotoSize;
import se.anyro.tgbotapi.types.file.UserProfilePhotos;

/**
 * Creates a photo message with the user info as caption.
 */
public class Profile extends Command {

    public Profile(TgBotApi api) {
        super(api);
    }

    @Override
    public String getDescription() {
        return "User profile info";
    }

    @Override
    public void run(Message message) throws IOException {
        
        User user = message.from;
        UserProfilePhotos userProfilePhotos = api.getUserProfilePhotos(user.id);

        StringBuilder builder = new StringBuilder();
        if (user.username != null) {
            builder.append("@").append(user.username).append('\n');
        }
        builder.append("Id: ").append(user.id).append('\n');
        builder.append("First: ").append(user.first_name).append('\n');
        if (user.last_name != null) {
            builder.append("Last: ").append(user.last_name).append('\n');
        }
        builder.append("Photos: ").append(userProfilePhotos.total_count);
        
        PhotoSize photo = userProfilePhotos.photos[0][0];
        api.sendPhoto(message.chat.id, photo.file_id, builder.toString(), 0, null);
    }
}