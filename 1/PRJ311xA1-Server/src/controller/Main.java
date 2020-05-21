package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public final String SERVER_NAME = "localhost";
    public final int PORT = 1234;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("../ui/ChatBox.fxml"));
        primaryStage.setTitle("Server Application");
        primaryStage.setScene(new Scene(root1, 650, 400));
        primaryStage.show();

        Stage stage2 = new Stage();
        Parent root2 = FXMLLoader.load(getClass().getResource("../ui/ServerBox.fxml"));
        stage2.setTitle("Server Application");
        stage2.setScene(new Scene(root2, 300, 275));
        stage2.show();

        //start the server and ready for client's connection

    }

    public static void main(String[] args) {
        launch(args);
    }
}
