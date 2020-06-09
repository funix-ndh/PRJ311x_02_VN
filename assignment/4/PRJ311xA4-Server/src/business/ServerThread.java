package business;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.entity.Client;
import com.entity.Server;

import controller.ServerBoxController;

public class ServerThread implements Runnable {
    private final ServerSocket serverSocket;
    public static final Map<String, ClientHandler> clients = new HashMap<>();

    public ServerThread(Server server) throws IOException {
        serverSocket = new ServerSocket(server.getPort());
    }

    @Override
    public void run() {
        try {
            while (true) {
                final Socket socket = serverSocket.accept();
                final DataInputStream dis = new DataInputStream(socket.getInputStream());
                final String line = dis.readUTF();
                final String username = line.substring(line.indexOf(':') + 1);
                final Client c = new Client(username, null, socket);
                clients.put(username, new ClientHandler(socket, c));
                ServerBoxController.clients.add(c);
            }
        } catch (IOException e) {
            System.out.println("Client connect error: " + e.getMessage());
        }
    }
}
