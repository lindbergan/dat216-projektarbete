package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;


public class CreditCardController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML private AnchorPane paymentViewCard;


    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton visa;
    @FXML private RadioButton mastercard;
    @FXML private RadioButton other;
    @FXML private TextField cardHolderName;
    private static boolean visited = false;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();


    @Override
    //sets the radiobuttons, visa = default choise
    public void initialize(URL url, ResourceBundle rb) {

        //makes sure that it has been visited in order to be able to jump pass it from the buttons in the header
        visited = true;

        visa.setSelected(true);
        visa.setToggleGroup(radioButtonGroup);
        mastercard.setToggleGroup(radioButtonGroup);
        other.setToggleGroup(radioButtonGroup);
    }

    //back to deliveryView when clicked <--
    public void backToDeliveryClicked(ActionEvent event)throws IOException {

        AnchorPane deliveryView = FXMLLoader.load(getClass().getResource("/fxml/DeliveryView.fxml"));
        paymentViewCard.getChildren().setAll(deliveryView);

    }

    //gives us the confirmation view
    public void continueClicked()throws IOException{

        AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml"));
        paymentViewCard.getChildren().setAll(confirmationView);
    }

    public static boolean hasBeenVisited(){
        return visited;
    }
}
