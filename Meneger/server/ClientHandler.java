import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private ServerSocketMain serverSocketMain;

    public ClientHandler(Socket socket, ServerSocketMain serverSocketMain) {
        this.clientSocket = socket;
        this.serverSocketMain = serverSocketMain;
    }

    @Override
    public void run() {
        try {
            in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Enter your username:");
            username = in.readLine();

            if (username == null || username.isBlank()) {
                out.println("❌ Invalid username. Disconnecting.");
                clientSocket.close();
                return;
            }

            serverSocketMain.clients.put(username, this);
            serverSocketMain.broadcast("🟢 " + username + " has joined the chat.");

            String message;
            while ((message = in.readLine()) != null) {
                String fullMessage = username + ": " + message;
                System.out.println("📩 " + fullMessage);
                ServerStorage.saveMessage(fullMessage);
                serverSocketMain.broadcast(fullMessage);
            }
        } catch (IOException e) {
            System.out.println("❌ Disconnected: " + username);
        } finally {
            try {
                if (username != null) {
                    serverSocketMain.clients.remove(username);
                    serverSocketMain.broadcast("🔴 " + username + " has left the chat.");
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}


