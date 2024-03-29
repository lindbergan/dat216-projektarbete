package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import properties.ViewChanger;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExitViewController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    private ShoppingCart cart = handler.getShoppingCart();
    private ViewChanger viewChanger = new ViewChanger();

    @FXML
    private AnchorPane exitView;
    @FXML
    private Label customerEmail;
    @FXML
    private Label customerDate;
    @FXML
    private Button receipts;
    @FXML
    private Button continueButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTextFields();
        initButtons();
        continueButton.setStyle("-fx-background-color:#A9D990");
    }

    public void initTextFields() {

        customerEmail.setText(customer.getEmail());
        customerDate.setText(DeliveryViewController.getUserSpecifiedDate() + " mellan kl " +
                DeliveryViewController.getUserSpecifiedTime());
    }

    public void initButtons() {
        receipts.setOnAction(e -> receiptsButtonPushed(e));
    }

    //clears the cart and goes back to the store when "continue shopping" button is pushed
    public void continueShopping(ActionEvent event) throws IOException {
        cart.clear();
        viewChanger.changeStage(event, exitView, "/fxml/IMat.fxml");
    }

    //goes back to iMatView and shows receipts
    public void receiptsButtonPushed(ActionEvent event) {
        //byt stage till kvitto-stage:n
        try {
            viewChanger.changeStage(event, exitView, "/fxml/IMat.fxml");
            viewChanger.changeScene(DataHolder.iMat.getContent(), "/fxml/Receipts.fxml/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //clears the cart and goes back to the store when "X" button is pushed
    public void closeWindow(ActionEvent event) throws IOException {
        cart.clear();
        viewChanger.changeStage(event, exitView, "/fxml/IMat.fxml");
    }
}
