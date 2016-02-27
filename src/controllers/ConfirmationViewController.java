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
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationViewController implements Initializable {

    @FXML private AnchorPane confirmationView;
    @FXML private AnchorPane exitView;
    @FXML private Label customerName;
    @FXML private Label customerAddress;
    @FXML private Label customerPostCode;
    @FXML private Label customerEmail;
    @FXML private Label customerPhone;
    @FXML private Label customerDate;


    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerName.setText(customer.getFirstName() +customer.getLastName());
        customerAddress.setText(customer.getAddress());
        customerPostCode.setText(customer.getPostCode());
        customerEmail.setText(customer.getEmail());
        customerPhone.setText(customer.getPhoneNumber());
        customerDate.setText(DeliveryViewController.getUserSpecifiedDate() + " " +
                DeliveryViewController.getUserSpecifiedMonth() + " mellan kl " +
                DeliveryViewController.getUserSpecifiedMinTime() + " - " +
                DeliveryViewController.getUserSpecifiedMaxTime()  );
    }

    //back to payment view when clicked <--
    public void backtoPaymentView()throws IOException {

        //villkor - beroende på om kund valt kort eller faktura, löses med observables och firepropertychange??

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
