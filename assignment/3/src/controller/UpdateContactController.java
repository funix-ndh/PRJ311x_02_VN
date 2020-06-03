package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import dao.GroupDAO;
import entity.Contact;
import entity.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateContactController {

    private static final Pattern onlyDigit = Pattern.compile("^\\d{10,11}$");
    private static final Pattern emailRegex = Pattern.compile(
            "^([a-zA-Z0-9_.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");

    public AnchorPane mainPanel;

    public TextField firstNameTxt;
    public TextField lastNameTxt;
    public TextField phoneTxt;
    public TextField emailTxt;
    public DatePicker dobPicker;
    public ComboBox<Group> cbGroup;

    public Label errFirstNameTxt;
    public Label errLastNameTxt;
    public Label errPhoneTxt;
    public Label errEmailTxt;
    public Label errDobTxt;
    public Label errGroupTxt;

    private ContactController contactController;
    private List<Contact> contactList;
    private int indexToUpdate;

    public void initialize() throws Exception {
        cbGroup.getItems().addAll(new GroupDAO().loadGroup("data/group.txt"));
    }

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public void setUpdatedContact(int index, Contact contact) throws Exception {
        indexToUpdate = index;
        firstNameTxt.setText(contact.getFirstName());
        lastNameTxt.setText(contact.getLastName());
        phoneTxt.setText(contact.getPhone());
        emailTxt.setText(contact.getEmail());
        dobPicker.setValue(LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(contact.getDob())));
        cbGroup.getSelectionModel().select(new Group(contact.getGroup()));
    }

    public void saveContact() throws Exception {
        if (isFormInvalid()) {
            return;
        }

        final Contact contact = new Contact(
                firstNameTxt.getText(),
                lastNameTxt.getText(),
                phoneTxt.getText(),
                emailTxt.getText(),
                dobPicker.getValue().toString(),
                cbGroup.getSelectionModel().getSelectedItem().getName()
        );

        final int indexFound = contactController.contactDAO.indexOf(contactList, contact);
        if (indexFound < 0 || indexFound == indexToUpdate) {
            contactController.contactDAO.updateContact(contactList, indexToUpdate, contact);
            contactController.showContact(contactList);
            contactController.contactDAO.saveToFile(contactList, "data/contact.txt");
            final Alert alert = new Alert(AlertType.CONFIRMATION, "Contact has been updated", ButtonType.OK);
            alert.setTitle("Confirmation");
            alert.showAndWait();
        } else {
            final Alert alert = new Alert(AlertType.ERROR, "Information of contact is existed");
            alert.setTitle("Information");
            alert.showAndWait();
        }
    }

    public void close() {
        ((Stage) mainPanel.getScene().getWindow()).close();
    }

    private boolean isFormInvalid() {
        boolean hasError = false;
        if (firstNameTxt.getText().trim().isEmpty()) {
            hasError = true;
            errFirstNameTxt.setVisible(true);
        } else {
            errFirstNameTxt.setVisible(false);
        }

        if (lastNameTxt.getText().trim().isEmpty()) {
            hasError = true;
            errLastNameTxt.setVisible(true);
        } else {
            errLastNameTxt.setVisible(false);
        }

        if (phoneTxt.getText().trim().isEmpty() || !onlyDigit.matcher(phoneTxt.getText()).matches()) {
            hasError = true;
            errPhoneTxt.setVisible(true);
        } else {
            errPhoneTxt.setVisible(false);
        }

        if (emailTxt.getText().trim().isEmpty() || !emailRegex.matcher(emailTxt.getText()).matches()) {
            hasError = true;
            errEmailTxt.setVisible(true);
        } else {
            errEmailTxt.setVisible(false);
        }

        if (dobPicker.getValue() == null) {
            hasError = true;
            errDobTxt.setVisible(true);
        } else {
            errDobTxt.setVisible(false);
        }

        if (cbGroup.getSelectionModel().getSelectedIndex() < 0) {
            hasError = true;
            errGroupTxt.setVisible(true);
        } else {
            errGroupTxt.setVisible(false);
        }

        return hasError;
    }
}
