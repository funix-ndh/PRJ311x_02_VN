package business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.entity.Client;

import javafx.scene.control.TextArea;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Client client;
    private DataOutputStream dos;
    private TextArea txtContent;

    public ClientHandler(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            final DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            while (true) {
                final String line = dis.readUTF();
                txtContent.appendText('\n' + client.getUsername() + ": " + line);
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public void send(String message) {
        try {
            dos.writeUTF(message);
            txtContent.appendText("\nMe: " + message);
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    public void setBox(TextArea txtContent) {
        this.txtContent = txtContent;
    }
}
