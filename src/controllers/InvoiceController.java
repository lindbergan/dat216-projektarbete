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

public class InvoiceController implements Initializable {


    @FXML private AnchorPane paymentViewInvoice;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton sameAsDelivery;
    @FXML private RadioButton otherAdress;



    @Override
    //sets the radiobuttons, visa = default choise
    public void initialize(URL url, ResourceBundle rb) {

        sameAsDelivery.setSelected(true);
        sameAsDelivery.setToggleGroup(radioButtonGroup);
        otherAdress.setToggleGroup(radioButtonGroup);
    }


    //back to delivery view when clicked <--
    public void backToDeliveryClicked()throws IOException {

        AnchorPane deliveryView = FXMLLoader.load(getClass().getResource("/fxml/DeliveryView.fxml/"));
        paymentViewInvoice.getChildren().setAll(deliveryView);
    }

    //gives us the confirmation view
    public void continueClicked()throws IOException{

        AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml/"));
        paymentViewInvoice.getChildren().setAll(confirmationView);

    }

}
