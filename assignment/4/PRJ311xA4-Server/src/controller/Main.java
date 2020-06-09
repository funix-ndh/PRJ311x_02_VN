package controller;

import com.entity.Server;

import business.ServerThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public final String SERVER_NAME = "localhost";
    public final int PORT = 8000;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("../ui/ServerBox.fxml"));
        primaryStage.setTitle("Server Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        //start the server and ready for client's connection
        try {
            final ServerThread serverThread = new ServerThread(new Server(SERVER_NAME, PORT));
            new Thread(serverThread).start();
        } catch (Exception e) {
            System.out.println("Couldn't start server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
