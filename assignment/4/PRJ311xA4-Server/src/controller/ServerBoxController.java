package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.entity.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ServerBoxController {

    public static ObservableList<Client> clients;

    public ListView<Client> listView;

    public void initialize() {
        clients = FXCollections.observableArrayList(new ArrayList<>());
        listView.setItems(clients);

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    final FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/ChatBox.fxml"));
                    final Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));
                    final String clientName = listView.getSelectionModel().getSelectedItem().getUsername();
                    stage.setTitle("Chat with " + clientName);
                    final ChatBoxController controller = loader.getController();
                    controller.init(clientName);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("Couldn't load window: " + e.getMessage());
                }
            }
        });
    }
}
