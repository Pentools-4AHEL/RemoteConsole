import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    Socket client = new Socket();

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    private void startServer(int port) {
        while (true) {
            try {
                ServerSocket server = new ServerSocket(port);
                client = server.accept();
                Runnable clientHandler = new ClientHandler(client);
                new Thread(clientHandler).start();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void run() {
        startServer(22122);
    }

}
