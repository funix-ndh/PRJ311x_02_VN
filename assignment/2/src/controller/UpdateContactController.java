package controller;

import entity.Contact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class UpdateContactController {

    public AnchorPane mainPanel;

    public void  setContactController(ContactController contactController) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }



    public  void setContacts(List<Contact> contacts) {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    public  void setUpdatedContact(Contact updatedContact) throws  Exception{
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    public  void updateContact(ActionEvent evt)throws  Exception {
        ((Stage)(mainPanel.getScene().getWindow())).close();
    }

    public  void saveContact() throws Exception {
        ((Stage)(mainPanel.getScene().getWindow())).close();
    }

}
