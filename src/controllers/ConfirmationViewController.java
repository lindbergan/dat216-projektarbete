package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import properties.CustomCell;
import properties.ViewChanger;
import properties.ViewSingelton;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ConfirmationViewController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    //private ObservableList<String> listViewList = FXCollections.observableArrayList("");
    ObservableList<String> listProductNames = FXCollections.observableArrayList();
    ObservableList<String> listProductQuantity = FXCollections.observableArrayList();
    ObservableList<String> listProductPricePerUnit = FXCollections.observableArrayList();
    ObservableList<String> listProductPrice = FXCollections.observableArrayList();
    ViewSingelton currentView = ViewSingelton.getInstance();
    private Customer customer = handler.getCustomer();
    private ShoppingCart shoppingCart = handler.getShoppingCart();
    private ObservableList<ShoppingItem> cartItems = FXCollections.observableArrayList(shoppingCart.getItems());
    private ViewChanger viewChanger = new ViewChanger();
    private ReceiptsController rc = new ReceiptsController();
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
    private Label customerPaymentChoice;
    @FXML
    private Label price;

    @FXML private Button confirmationButton;
    private ObservableList<String> listViewList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirmationButton.setStyle("-fx-background-color:#A9D990");
        initTextFields();
        initShoppingCartSummary();
        setTotalPrice();
    }

    public void initShoppingCartSummary() {

        for (Iterator<ShoppingItem> ite = cartItems.iterator(); ite.hasNext(); ) {

            ShoppingItem thisItem = ite.next();
            String totalItemCost;
            if(thisItem.getAmount() * thisItem.getProduct().getPrice() == Math.floor(thisItem.getAmount() * thisItem.getProduct().getPrice())){
                totalItemCost = (int)(thisItem.getAmount() * thisItem.getProduct().getPrice())+"";
            }else{
                totalItemCost = String.format("%.2f", thisItem.getAmount() * thisItem.getProduct().getPrice());
            }
            //double totalItemCost = thisItem.getAmount() * thisItem.getProduct().getPrice();

            String pricePerUnit;
            if(thisItem.getProduct().getPrice() == Math.floor(thisItem.getProduct().getPrice())){
                pricePerUnit = (int)thisItem.getProduct().getPrice()+"";
            }else{
                pricePerUnit = String.format("%.2f", thisItem.getProduct().getPrice());
            }
            listProductNames.add(thisItem.getProduct().getName());
            listProductQuantity.add(String.valueOf(thisItem.getAmount()));
            listProductPricePerUnit.add(pricePerUnit);
            listProductPrice.add(totalItemCost);
            listViewList.add(getStringSpacing(thisItem.getProduct().getName() + ", " +
                    thisItem.getAmount() + " x " + pricePerUnit +
                    " " + thisItem.getProduct().getUnit()) + totalItemCost + ":-");

        }

        shoppingCartSummary.setItems(listViewList);

        shoppingCartSummary.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {

                return new CustomCell();

            }
        });
    }

    public String getStringSpacing(String str) {

        while (str.length() < 50) {
            str = str + " ";
        }
        return str;
    }

    public void initTextFields() {

        customerName.setText(customer.getFirstName() + " " + customer.getLastName());
        customerAddress.setText(customer.getAddress());
        customerPostCode.setText(customer.getPostCode());
        customerEmail.setText(customer.getEmail());
        customerPhone.setText(customer.getPhoneNumber());
        customerDate.setText(DeliveryViewController.getUserSpecifiedDate() + " mellan kl " +
                DeliveryViewController.getUserSpecifiedTime());

        customerPaymentChoice.setText(DeliveryViewController.getPaymentChoice());
    }

    public void setTotalPrice() {
        price.setText(shoppingCart.getTotal() + " :-");
    }

    //back to the correct PaymentView when "go back" <-- is clicked
    public void backToPaymentView() throws IOException {

        currentView.setCurrentViewName("paymentView");
        DataHolder.deliveryViewController.setPaymentProgImage();

        //need to determine what View to present - based on users Paymentchoice
        if (DeliveryViewController.getPaymentChoice() == "Kortbetalning") {
            viewChanger.changeScene(confirmationView, "/fxml/PaymentViewCard.fxml");
        } else {
            viewChanger.changeScene(confirmationView, "/fxml/PaymentViewInvoice.fxml");
        }
    }

    //go to ExitView
    public void confirmPurchase(ActionEvent event) throws IOException {
        handler.placeOrder(true);
        viewChanger.changeStageOverride(event, "/fxml/ExitView.fxml");
    }
}
