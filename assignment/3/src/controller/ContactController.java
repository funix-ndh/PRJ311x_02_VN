package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.ContactDAO;
import dao.GroupDAO;
import entity.Contact;
import entity.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContactController {

    public BorderPane mainPanel;
    public TableView<Contact> contactsData;
    public ComboBox<Group> comboBoxGroup;
    public TextField searchTxt;

    public ContactDAO contactDAO;
    private List<Contact> contactList;

    public void initialize() throws Exception {
        contactDAO = new ContactDAO();
        contactList = contactDAO.loadContact("data/contact.txt");

        showGroup(new GroupDAO().loadGroup("data/group.txt"));
        showContact(contactList);
    }

    //output all contact to tblContact
    public void showContact(List<Contact> list) {
        contactsData.getItems().clear();
        contactsData.getItems().addAll(list);
    }

    //output all groups to dropdown list
    public void showGroup(List<Group> list) {
        comboBoxGroup.getItems().clear();
        comboBoxGroup.getItems().add(new Group("ALL"));
        comboBoxGroup.getItems().addAll(list);
        comboBoxGroup.getSelectionModel().select(0);
    }

    //do corresponding actions for search, delete, update and add contact
    public void searchContact() throws Exception {
        final int selectedIndex = comboBoxGroup.getSelectionModel().getSelectedIndex();
        final String groupSearchKey =
                selectedIndex < 0 ? "ALL" : comboBoxGroup.getSelectionModel().getSelectedItem().getName();
        final List<Contact> founds = contactDAO.search(contactList, groupSearchKey, searchTxt.getText());
        showContact(founds);
    }

    //manage the groups
    public void groupPanel() throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //update a contact
    public void updateContact() throws Exception {
        final int index = contactsData.getSelectionModel().getSelectedIndex();
        if (index < 0 || index >= contactList.size()) {
            final Alert alert = new Alert(AlertType.ERROR, "Select a contact to update");
            alert.setTitle("Information");
            alert.showAndWait();
        } else {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../ui/updateContact.fxml"));
            final Stage stage = new Stage();
            stage.initOwner(mainPanel.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(new Scene(loader.load()));
            stage.show();

            final UpdateContactController controller = loader.getController();
            controller.setContactController(this);
            controller.setContactList(contactList);
            controller.setUpdatedContact(
                    contactDAO.indexOf(contactList, contactsData.getSelectionModel().getSelectedItem()),
                    contactsData.getSelectionModel().getSelectedItem());
        }
    }

    //delete a selected contact
    public void deleteContact() throws Exception {
        final int index = contactsData.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            final Alert alert = new Alert(AlertType.ERROR, "Select a contact to delete");
            alert.setTitle("Information");
            alert.showAndWait();
        } else {
            final Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wanna delete selected contact?");
            alert.setTitle("Confirmation");
            final Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                contactList.remove(contactsData.getSelectionModel().getSelectedItem());
                showContact(contactList);
                contactDAO.saveToFile(contactList, "data/contact.txt");
            }
        }
    }

    public void addContact() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../ui/addContact.fxml"));
        final Stage stage = new Stage();
        stage.initOwner(mainPanel.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(loader.load()));
        stage.show();

        final AddContactController controller = loader.getController();
        controller.setAddContactController(this);
        controller.setContactList(contactList);
    }

    public void manageGroup() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../ui/group.fxml"));
        final Stage stage = new Stage();
        stage.initOwner(mainPanel.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(loader.load()));
        stage.show();

        final GroupController controller = loader.getController();
        controller.setContactController(this);
        controller.setContactList(contactList);
    }
}
