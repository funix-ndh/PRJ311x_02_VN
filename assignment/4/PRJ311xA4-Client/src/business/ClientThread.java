package business;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.entity.Server;

import javafx.scene.control.TextArea;

public class ClientThread implements Runnable {
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final TextArea txtContent;

    public ClientThread(Server server, TextArea txtContent) throws IOException {
        this.txtContent = txtContent;
        final Socket socket = new Socket(server.getHost(), server.getPort());
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                final String readLine = dis.readUTF();
                txtContent.appendText("\nServer: " + readLine);
            } catch (IOException e) {
                System.out.println("read input stream error: " + e.getMessage());
            }
        }
    }

    public void send(String line) throws Exception {
        dos.writeUTF(line);
        if (!line.startsWith(":")) {
            txtContent.appendText("\nMe: " + line);
        }
    }
}
