package controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;

/**
 * Created by Razmus on 2016-02-28.
 */
public class HelpMenuController {

    //closes help menu when pressing the button
    public void closeWindow(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
