package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationViewController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    ShoppingCart cart = handler.getShoppingCart();


    @FXML
    private AnchorPane confirmationView;
    @FXML
    private ListView shoppingCartSummary;
    @FXML
    private Label customerName;
    @FXML
    private Label customerAddress;
    @FXML
    private Label customerPostCode;
    @FXML
    private Label customerEmail;
    @FXML
    private Label customerPhone;
    @FXML
    private Label customerDate;
    @FXML
    private Label customerPaymentChoise;
    @FXML private Label price;
    private ViewChanger viewChanger = new ViewChanger();
    private Customer customer = handler.getCustomer();
    private ShoppingCart shoppingCart = handler.getShoppingCart();
    private ObservableList<ShoppingItem> cartItems = FXCollections.observableArrayList(shoppingCart.getItems());


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTextFields();
        setShoppingCartSummary();
        setTotalPrice();
    }

    public void initTextFields() {

        customerName.setText(customer.getFirstName() + " " + customer.getLastName());
        customerAddress.setText(customer.getAddress());
        customerPostCode.setText(customer.getPostCode());
        customerEmail.setText(customer.getEmail());
        customerPhone.setText(customer.getPhoneNumber());
        customerDate.setText(DeliveryViewController.getUserSpecifiedDate() + " " +
                DeliveryViewController.getUserSpecifiedMonth() + " mellan kl " +
                DeliveryViewController.getUserSpecifiedMinTime() + " - " +
                DeliveryViewController.getUserSpecifiedMaxTime());

        customerPaymentChoise.setText(DeliveryViewController.getPaymentChoise());
    }

    //skriver denna senare
    public void setShoppingCartSummary(){
    }

    public void setTotalPrice(){
        price.setText(cart.getTotal() + " :-");
    }


    //back to the correct PaymentView when "go back" <-- is clicked
    public void backtoPaymentView() throws IOException {

        //need to determine what View to present - based on users Paymentchoise
        if (DeliveryViewController.getPaymentChoise() == "Kortbetalning") {

            viewChanger.changeScene(confirmationView, "/fxml/PaymentViewCard.fxml");
        } else {

            viewChanger.changeScene(confirmationView, "/fxml/PaymentViewInvoice.fxml");
        }
    }

    //go to ExitView
    public void confirmPurches(ActionEvent event) throws IOException {

        viewChanger.changeStageOverride(event, "/fxml/ExitView.fxml");
    }
}
