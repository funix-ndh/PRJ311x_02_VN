package controller;

import java.io.IOException;
import java.util.List;

import entity.Contact;
import entity.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContactController {

    public BorderPane mainPanel;
    public TableView<Contact> contactsData;

    void initialize() {
    }

    //output all contact to tblContact
    public void showContact(List<Contact> c) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //output all groups to dropdownlist
    public void showGroup(List<Group> g) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //do corresponding actions for search, delete, update and add contact
    public void searchContact(ActionEvent evt) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");

    }

    //manage the groups
    public void groupPanel() throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");

    }

    //update a contact
    public void addContact() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/addContact.fxml"));
        Stage stage = new Stage();
        stage.initOwner(mainPanel.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.showAndWait();
    }

    //update a contact
    public void updateContact() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/updateContact.fxml"));
        Stage stage = new Stage();
        stage.initOwner(mainPanel.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.showAndWait();
    }

    //delete a selected contact
    public void deleteContact() throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation");
        alert.setContentText("Do you wanna delete selected contact?");
        alert.showAndWait();
    }

    public void manageGroup(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../ui/group.fxml"));
        Stage stage = new Stage();
        stage.initOwner(mainPanel.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.showAndWait();
    }
}
