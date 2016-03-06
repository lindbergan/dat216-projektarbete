package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class HelpMenuController {

    //closes help menu when pressing the button
    public void closeWindow(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
