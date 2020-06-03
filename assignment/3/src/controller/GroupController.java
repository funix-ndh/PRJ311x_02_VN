package controller;

import java.util.List;
import java.util.Optional;

import dao.GroupDAO;
import entity.Contact;
import entity.Group;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GroupController {

    public AnchorPane mainPanel;
    public ListView<Group> tblGroup;
    public TextField groupNameTxt;
    public TextField searchTxt;

    private GroupDAO groupDao;
    private List<Group> groupList;
    private List<Contact> contactList;
    private ContactController contactController;

    public void initialize() throws Exception {
        groupDao = new GroupDAO();
        groupList = groupDao.loadGroup("data/group.txt");
        showGroup(groupList);
        tblGroup.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblGroup.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (tblGroup.getSelectionModel().getSelectedItem() != null) {
                groupNameTxt.setText(tblGroup.getSelectionModel().getSelectedItem().getName());
            }
        });
    }

    public void setContactController(ContactController contactController) {
        this.contactController = contactController;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    //search action
    public void searchAction() {
        showGroup(groupDao.search(groupList, searchTxt.getText()));
    }

    //add new group action
    public void addAction() throws Exception {
        final String groupName = groupNameTxt.getText().trim();
        if (groupName.isEmpty()) {
            final Alert alert = new Alert(AlertType.ERROR, "Group name cannot be empty");
            alert.setTitle("ERROR");
            alert.showAndWait();
        } else {
            final Group group = new Group(groupName);
            if (groupDao.indexOf(groupList, group) < 0) {
                groupDao.saveGroupToList(groupList, group);
                groupDao.saveGroupToFile(groupList, "data/group.txt");
                showGroup(groupList);
                contactController.showGroup(groupList);
            } else {
                final Alert alert = new Alert(AlertType.ERROR
                        , "Group name existed already, choose another name");
                alert.setTitle("ERROR");
                alert.showAndWait();
            }
        }
    }

    //update group name
    public void updateAction() throws Exception {
        if (tblGroup.getSelectionModel().getSelectedItem() == null) {
            final Alert alert = new Alert(AlertType.ERROR, "Select a group to update");
            alert.setTitle("ERROR");
            alert.showAndWait();
        } else {
            final Group oldGroup = tblGroup.getSelectionModel().getSelectedItem();
            final String oldGroupName = oldGroup.getName();
            final int originalIndex = groupDao.indexOf(groupList, oldGroup);

            final String newGroupName = groupNameTxt.getText().trim();
            if (groupDao.indexOf(groupList, new Group(newGroupName)) < 0) {
                groupList.get(originalIndex).setName(newGroupName);
                groupDao.saveGroupToFile(groupList, "data/group.txt");
                showGroup(groupList);
                contactController.showGroup(groupList);

                for (Contact c : contactList) {
                    if (c.getGroup().equalsIgnoreCase(oldGroupName)) {
                        c.setGroup(newGroupName);
                    }
                }
                contactController.showContact(contactList);
                contactController.contactDAO.saveToFile(contactList, "data/contact.txt");
                final Alert alert = new Alert(AlertType.CONFIRMATION, "A Group has been updated", ButtonType.OK);
                alert.setTitle("Confirmation");
                alert.showAndWait();
            } else {
                final Alert alert = new Alert(AlertType.ERROR
                        , "Group name existed already, choose another name");
                alert.setTitle("ERROR");
                alert.showAndWait();
            }
        }
    }

    //delete a group, delete failed if there are some contact is in deleted one
    public void deleteAction() throws Exception {
        if (tblGroup.getSelectionModel().getSelectedItem() == null) {
            final Alert alert = new Alert(AlertType.ERROR, "Select a group to delete");
            alert.setTitle("ERROR");
            alert.showAndWait();
        } else {
            final Alert alert = new Alert(AlertType.CONFIRMATION, "Do you wanna delete selected group?");
            alert.setTitle("Confirmation");
            final Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                final Group group = tblGroup.getSelectionModel().getSelectedItem();
                groupList.remove(group);
                groupDao.saveGroupToFile(groupList, "data/group.txt");
                showGroup(groupList);
                contactController.showGroup(groupList);

                contactList.removeIf(c -> c.getGroup().equalsIgnoreCase(group.getName()));
                contactController.showContact(contactList);
                contactController.contactDAO.saveToFile(contactList, "data/contact.txt");
            }
        }
    }

    //operations on each button on window
    public void groupAction(ActionEvent evt) throws Exception {
        throw new UnsupportedOperationException("Remove this line and implement your code here!");
    }

    //output all groups to table view
    public void showGroup(List<Group> groupList) {
        tblGroup.getItems().clear();
        tblGroup.getItems().addAll(groupList);
    }

    public void close() {
        ((Stage) mainPanel.getScene().getWindow()).close();
    }
}
