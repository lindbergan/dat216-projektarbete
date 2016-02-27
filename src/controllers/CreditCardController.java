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

public class CreditCardController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML private AnchorPane paymentViewCard;


    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton visa;
    @FXML private RadioButton mastercard;
    @FXML private RadioButton other;



    @Override
    //sets the radiobuttons, visa = default choise
    public void initialize(URL url, ResourceBundle rb) {

        visa.setSelected(true);
        visa.setToggleGroup(radioButtonGroup);
        mastercard.setToggleGroup(radioButtonGroup);
        other.setToggleGroup(radioButtonGroup);
    }

    //back to deliveryView when clicked <--
    public void backToDeliveryClicked(ActionEvent event)throws IOException {

        Parent checkoutParent = FXMLLoader.load(getClass().getResource("/fxml/CheckoutView.fxml/"));
        Scene checkoutScene = new Scene(checkoutParent);
        Stage checkoutStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        checkoutStage.hide();
        checkoutStage.setScene(checkoutScene);
        checkoutStage.show();
    }

    //gives us the confirmation view
    public void continueClicked()throws IOException{

        AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml/"));
        paymentViewCard.getChildren().setAll(confirmationView);

    }
}
