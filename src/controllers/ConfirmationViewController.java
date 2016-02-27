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
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationViewController implements Initializable {

    @FXML private AnchorPane confirmationView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //back to payment view when clicked <--
    public void backtoPaymentView()throws IOException {

        //villkor - beroende på om kund valt kort eller faktura,

        // kort blir:
        AnchorPane paymentViewCard = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml/"));
        confirmationView.getChildren().setAll(paymentViewCard);
    }

    //gives us the Exit/Thank you view
    public void confirmPurches(ActionEvent event) throws IOException{
        Parent exitParent = FXMLLoader.load(getClass().getResource("/fxml/ExitView.fxml/"));
        Scene exitScene = new Scene(exitParent);
        Stage exitStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        exitStage.hide();
        exitStage.setScene(exitScene);
        exitStage.show();
    }

}
