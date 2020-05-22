package sample;

import javafx.scene.control.TextField;
import sample.datamodel.Contact;

public class ContactController {

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField phoneNumberField;
    public TextField notesField;

    public Contact getContact() {
        return new Contact(firstNameField.getText(),
                           lastNameField.getText(),
                           phoneNumberField.getText(),
                           notesField.getText());
    }

    public void editContact(Contact selectedContact) {
        firstNameField.setText(selectedContact.getFirstName());
        lastNameField.setText(selectedContact.getLastName());
        phoneNumberField.setText(selectedContact.getPhoneNumber());
        notesField.setText(selectedContact.getNotes());
    }

    public void updateContact(Contact selectedContact) {
        selectedContact.setFirstName(firstNameField.getText());
        selectedContact.setLastName(lastNameField.getText());
        selectedContact.setPhoneNumber(phoneNumberField.getText());
        selectedContact.setNotes(notesField.getText());
    }
}
