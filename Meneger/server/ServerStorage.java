import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ServerStorage {
    private static final String FILE_PATH = "data/messages/chat_history.txt";

    public static synchronized void saveMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String timestamp = "[" + LocalDateTime.now() + "] ";
            writer.write(timestamp + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("❌ Error saving message: " + e.getMessage());
        }
    }
}