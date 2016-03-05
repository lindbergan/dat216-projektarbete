package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectedListController implements Initializable {
    String name;
    @FXML
    private Label listName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listName.setText(name);
    }

    public void setListName(String name) {
        this.name = name;
    }
}
