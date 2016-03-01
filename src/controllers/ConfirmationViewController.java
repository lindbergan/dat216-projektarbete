package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ConfirmationViewController implements Initializable {

    @FXML private AnchorPane confirmationView;
    @FXML private ListView shoppingCartSummary;
    @FXML private Label customerName;
    @FXML private Label customerAddress;
    @FXML private Label customerPostCode;
    @FXML private Label customerEmail;
    @FXML private Label customerPhone;
    @FXML private Label customerDate;
    @FXML private Label customerPaymentChoise;


    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    private ShoppingCart shoppingCart =handler.getShoppingCart();
    private ObservableList<ShoppingItem> cartItems = FXCollections.observableArrayList(shoppingCart.getItems());


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTextFields();

        //places the shopping items in the ObservableList if the shoppingcart contains any items
        if(shoppingCart.getItems().size() != 0) {
            shoppingCartSummary.setItems(cartItems);
        }
    }

    //back to payment view when clicked <--
    public void backtoPaymentView()throws IOException {

        //need to determine what View to present - based on users Paymentchoise
        if(DeliveryViewController.getPaymentChoise()=="Kortbetalning") {

            AnchorPane paymentViewCard = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml"));
            confirmationView.getChildren().setAll(paymentViewCard);
        }
        else{
            AnchorPane paymentViewInvoice = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewInvoice.fxml"));
            confirmationView.getChildren().setAll(paymentViewInvoice);
        }
    }

    //gives us the Exit/Thank you view
    public void confirmPurches(ActionEvent event) throws IOException{
        Parent exitParent = FXMLLoader.load(getClass().getResource("/fxml/ExitView.fxml"));
        Scene exitScene = new Scene(exitParent, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Stage exitStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        exitStage.hide();
        exitStage.setScene(exitScene);
        exitStage.show();
    }

    public void initTextFields(){

        customerName.setText(customer.getFirstName() + " " + customer.getLastName());
        customerAddress.setText(customer.getAddress());
        customerPostCode.setText(customer.getPostCode());
        customerEmail.setText(customer.getEmail());
        customerPhone.setText(customer.getPhoneNumber());
        customerDate.setText(DeliveryViewController.getUserSpecifiedDate() + " " +
                DeliveryViewController.getUserSpecifiedMonth() + " mellan kl " +
                DeliveryViewController.getUserSpecifiedMinTime() + " - " +
                DeliveryViewController.getUserSpecifiedMaxTime()  );

        customerPaymentChoise.setText(DeliveryViewController.getPaymentChoise());
    }

}
