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
import se.chalmers.ait.dat215.project.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class ConfirmationViewController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    private ShoppingCart shoppingCart = handler.getShoppingCart();
    private ObservableList<ShoppingItem> cartItems = FXCollections.observableArrayList(shoppingCart.getItems());
    private ViewChanger viewChanger = new ViewChanger();

    @FXML
    private AnchorPane confirmationView;
    @FXML
    private ListView<String> shoppingCartSummary = new ListView<String>();
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

    private List<String> shoppingcartToString  = new ArrayList<String>();
    private ObservableList<String> testList = FXCollections.observableArrayList("a","b","c","d","a","b","c","d","a","b","c","d","a","b","c","d");
    private ObservableList<String> listViewList = FXCollections.observableArrayList(shoppingcartToString); //"a","b","c","d","a","b","c","d","a","b","c","d","a","b","c","d"
    private double totalItemCost;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTextFields();
        //setShoppingCartSummary();
        setTotalPrice();

        constructListViewList();
        //vill ta in en hel lista, måste skapa denna lista innan
        //shoppingCartSummary.setItems(listViewList);



    }

    public void constructListViewList(){

        for(Iterator<ShoppingItem> ite = cartItems.iterator(); ite.hasNext(); ) {

            ShoppingItem thisItem = ite.next();
            double totalItemCost = thisItem.getAmount()*thisItem.getProduct().getPrice();

            listViewList.add(thisItem.getProduct().getName() + "                                        " + thisItem.getProduct().getPrice() + ":-" + " * " + thisItem.getAmount() + " " +thisItem.getProduct().getUnit() +"                                 " + totalItemCost + ":-");
        }

        for(int i = 0; i<listViewList.size(); i++){
            System.out.println(listViewList.get(i));
        }
        shoppingCartSummary.setItems(listViewList);
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

    public void setTotalPrice(){
        price.setText(shoppingCart.getTotal() + " :-");
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
