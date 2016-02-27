package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExitViewController implements Initializable {

    @FXML private AnchorPane exitView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //goes back to the store
    public void continueShopping(ActionEvent event) throws IOException{
        Parent imatParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml/"));
        Scene imatScene = new Scene(imatParent);
        Stage imatStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        imatStage.hide();
        imatStage.setScene(imatScene);
        imatStage.show();
    }
    public void closeWindow(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
