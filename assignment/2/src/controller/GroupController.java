package controller;

import entity.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;

public class GroupController {

    public AnchorPane mainPanel;

    //search action
    public  void searchAction() {
        close();
    }

    //add new group action
    public  void addAction()throws Exception {
        close();
    }

    //update group name
    public  void updateAction() {
        close();
    }

    //delete a group, delete failed if there are some contact is in deleted one
    public  void deleteAction()throws Exception {
        close();
    }

    //operations on each button on window
    public  void groupAction(ActionEvent evt)throws Exception {
    }

    //output all groups to table view
    public  void showGroup(List<Group> groups) {
    }

    public void close() {
        ((Stage)(mainPanel.getScene().getWindow())).close();
    }
}
