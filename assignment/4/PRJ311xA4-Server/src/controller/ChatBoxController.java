package controller;

import business.ClientHandler;
import business.ServerThread;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBoxController {

    public TextField txtMessage;
    public TextArea txtContent;
    private ClientHandler clientHandler;

    public void init(String clientName) {
        clientHandler = ServerThread.clients.get(clientName);
        new Thread(clientHandler).start();
        clientHandler.setBox(txtContent);
    }

    public void onSendActionPerformed() {
        clientHandler.send(txtMessage.getText());
        txtMessage.setText("");
    }
}
