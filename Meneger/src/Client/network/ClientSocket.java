import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private MessageListener messageListener;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public ClientSocket(String host, int port) throws IOException {
        socket = new Socket(host, port);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Start background thread to listen for messages from the server
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    if (messageListener != null) {
                        messageListener.onMessageReceived(message);
                    }
                }
            } catch (IOException e) {
                System.out.println("❌ Disconnected from server.");
                if (messageListener != null) {
                    messageListener.onMessageReceived("❌ Lost connection to server.");
                }
            }
        }).start();
    }

    public void sendUsername(String username) {
        out.println(username);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }

    public static void main(String[] args) {
        // Entry point for the program
    }
}