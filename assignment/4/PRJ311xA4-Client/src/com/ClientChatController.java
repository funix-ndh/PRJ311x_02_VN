package com;

import com.entity.Client;
import com.entity.Server;

import business.ClientThread;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientChatController {

    public TextField txtHost, txtPort, txtUsername, txtMessage;
    public TextArea txtContent;

    private ClientThread workerThread;

    public void onConnectActionPerformed() {
        try {
            final Client client = new Client(txtUsername.getText(), "");
            final Server server = new Server(txtHost.getText(), Integer.valueOf(txtPort.getText()));
            workerThread = new ClientThread(server, txtContent);
            new Thread(workerThread).start();
            workerThread.send(':' + client.getUsername());
        } catch (Exception e) {
            System.out.println("Something wrong: " + e.getMessage());
        }
    }

    public void onSendActionPerformed() {
        try {
            workerThread.send(txtMessage.getText());
            txtMessage.setText("");
        } catch (Exception e) {
            System.out.println("Something wrong: " + e.getMessage());
        }
    }
}
