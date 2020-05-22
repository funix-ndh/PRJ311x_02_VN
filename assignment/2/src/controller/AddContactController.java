package controller;

import java.util.List;

import entity.Contact;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddContactController {

    public AnchorPane mainPanel;

    public void  setAddContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }


    private List<Contact> contacts;

    public  void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public  void saveContact() throws Exception {
        ((Stage)(mainPanel.getScene().getWindow())).close();
    }
}
