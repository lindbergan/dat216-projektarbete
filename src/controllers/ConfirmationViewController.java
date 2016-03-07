package controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import properties.CustomCell;
import properties.ViewChanger;
import properties.ViewSingelton;
import se.chalmers.ait.dat215.project.*;
import java.io.IOException;
import java.net.URL;
import java.nio.CharBuffer;
import java.time.Instant;
import java.util.*;
import java.io.FileWriter;

public class ConfirmationViewController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
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

    //private ObservableList<String> listViewList = FXCollections.observableArrayList("");
    ObservableList<String> listProductNames = FXCollections.observableArrayList();
    ObservableList<String> listProductQuantity = FXCollections.observableArrayList();
    ObservableList<String> listProductPricePerUnit = FXCollections.observableArrayList();
    ObservableList<String> listProductPrice = FXCollections.observableArrayList();
    private ObservableList<String> listViewList = FXCollections.observableArrayList();
    ViewSingelton currentView = ViewSingelton.getInstance();



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTextFields();
        initShoppingCartSummary();
        setTotalPrice();
    }

    public void initShoppingCartSummary() {

        for (Iterator<ShoppingItem> ite = cartItems.iterator(); ite.hasNext(); ) {

            ShoppingItem thisItem = ite.next();
            double totalItemCost = thisItem.getAmount()*thisItem.getProduct().getPrice();

            listProductNames.add(thisItem.getProduct().getName());
            listProductQuantity.add(String.valueOf(thisItem.getAmount()));
            listProductPricePerUnit.add(String.valueOf(thisItem.getProduct().getPrice()));
            listProductPrice.add(String.valueOf(totalItemCost));

            listViewList.add(getStringSpacing(thisItem.getProduct().getName() +", " +
                    thisItem.getAmount() + " x " + thisItem.getProduct().getPrice() +
                            " " + thisItem.getProduct().getUnit()) +totalItemCost + ":-");

        }

        shoppingCartSummary.setItems(listViewList);

        shoppingCartSummary.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {

                    return new CustomCell();

            }
        });
    }

    public String getStringSpacing(String str){

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

        //need to determine what View to present - based on users Paymentchoice
        if (DeliveryViewController.getPaymentChoice() == "Kortbetalning") {
            viewChanger.changeScene(confirmationView, "/fxml/PaymentViewCard.fxml");
        } else {
            viewChanger.changeScene(confirmationView, "/fxml/PaymentViewInvoice.fxml");
        }
    }

    //go to ExitView
    public void confirmPurchase(ActionEvent event) throws IOException {
        saveAllInfo();
        handler.getShoppingCart().clear();
        viewChanger.changeStageOverride(event, "/fxml/ExitView.fxml");
    }

    public void saveAllInfo() throws IOException{
        try {
            double quantitySum = 0;
            double priceSum = 0;
            FileWriter writer = new FileWriter("receipts.txt", true);
            writer.append("date;");
            writer.append(String.valueOf(Date.from(Instant.now())) + ";");
            writer.append("name;");
            writer.append(customer.getFirstName() + ";");
            writer.append(customer.getLastName() + ";");
            for (int startNr = 0; startNr < listProductNames.size(); startNr++) {
                writer.append("pid;");
                writer.append(listProductNames.get(startNr) + ";");
                writer.append("pamount");
                writer.append(listProductQuantity.get(startNr) + ";");
                writer.append("pprice");
                writer.append(listProductPricePerUnit.get(startNr) + ";");
                writer.append("ptotal");
                writer.append(listProductPrice.get(startNr) + ";");
                quantitySum = quantitySum + Double.valueOf(listProductQuantity.get(startNr));
                priceSum = priceSum + Double.valueOf(listProductPrice.get(startNr));
            }
            writer.append("price;");
            writer.append(String.valueOf(priceSum) + ";");
            writer.append("quantity;");
            writer.append(String.valueOf(quantitySum) + ";");
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
