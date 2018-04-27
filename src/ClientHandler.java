import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    Socket client;
    DataInputStream dis;
    DataOutputStream dos;

    public ClientHandler(Socket client) {
        this.client = client;
    }
    public void run() {
        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server: Client connected");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            try {
                dos.writeUTF(input);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String output;
            try {
                output = dis.readUTF();
                System.out.println(""+output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

