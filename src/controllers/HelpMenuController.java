package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class HelpMenuController {

    @FXML
    AnchorPane pane1;
    @FXML
    AnchorPane pane2;
    @FXML
    AnchorPane pane3;
    @FXML
    AnchorPane pane4;
    @FXML
    AnchorPane pane5;
    @FXML
    AnchorPane pane6;

    public void forwardClicked(){
        if(pane1.isVisible()){
            pane1.setVisible(false);
            pane2.setVisible(true);
            System.out.println("hej");
        }
        else
        if(pane2.isVisible()){
            pane2.setVisible(false);
            pane3.setVisible(true);
        }
        else
        if(pane3.isVisible()){
            pane3.setVisible(false);
            pane4.setVisible(true);
        }
        else
        if(pane4.isVisible()){
            pane4.setVisible(false);
            pane5.setVisible(true);
        }
        else
        if(pane5.isVisible()){
            pane5.setVisible(false);
            pane6.setVisible(true);
        }
        else
        if(pane6.isVisible()){
            pane6.setVisible(false);
            pane1.setVisible(true);
        }
    }

    public void backClicked(){
        if(pane1.isVisible()){
            pane1.setVisible(false);
            pane6.setVisible(true);
        }
        if(pane2.isVisible()){
            pane2.setVisible(false);
            pane1.setVisible(true);
        }
        if(pane3.isVisible()){
            pane3.setVisible(false);
            pane2.setVisible(true);
        }
        if(pane4.isVisible()){
            pane4.setVisible(false);
            pane3.setVisible(true);
        }
        if(pane5.isVisible()){
            pane5.setVisible(false);
            pane4.setVisible(true);
        }
        if(pane6.isVisible()){
            pane6.setVisible(false);
            pane5.setVisible(true);
        }
    }

    //closes help menu when pressing the button
    public void closeWindow(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
