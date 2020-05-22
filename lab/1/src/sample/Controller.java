package sample;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

public class Controller {
    public BorderPane mainPanel;
    public TableView<Contact> contactsTable;

    private ContactData data;

    public void initialize() {
        data = new ContactData();
        data.loadContacts();
        contactsTable.setItems(data.getContacts());
    }

    public void showContactDialog(ActionEvent actionEvent) throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add New Contact");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        dialog.getDialogPane().setContent(fxmlLoader.load());
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactController contactController = fxmlLoader.getController();
            Contact newContact = contactController.getContact();
            data.addContact(newContact);
            data.saveContacts();
        }
    }

    public void showEditDialog(ActionEvent actionEvent) throws IOException {
        Contact selectedContact = contactsTable.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText("Please select the contact you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        dialog.getDialogPane().setContent(fxmlLoader.load());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        ContactController contactController = fxmlLoader.getController();
        contactController.editContact(selectedContact);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            contactController.updateContact(selectedContact);
            data.saveContacts();
        }
    }

    public void deleteContact(ActionEvent actionEvent) {
        Contact selectContact = contactsTable.getSelectionModel().getSelectedItem();
        if (selectContact == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText(null);
        alert.setContentText(
                "Are you sure you want to delete the selected contact: " + selectContact.getFirstName() + ' '
                + selectContact.getLastName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            data.deleteContact(selectContact);
            data.saveContacts();
        }
    }
}
