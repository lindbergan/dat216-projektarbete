package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import properties.ViewChanger;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private ViewChanger viewChanger = new ViewChanger();
    final ToggleGroup radioButtonGroup = new ToggleGroup();

    @FXML
    private AnchorPane paymentViewInvoice;
    @FXML
    private RadioButton sameAsDelivery;
    @FXML
    private RadioButton otherAdress;
    private Customer customer = handler.getCustomer();
    @FXML
    private TextField invoiceFirstName;
    @FXML
    private TextField invoiceLastName;
    @FXML
    private TextField invoiceAddress;
    @FXML
    private TextField invoicePostCode;
    @FXML
    private TextField invoicePostAddress;
    @FXML
    private TextField invoiceEmail;
    @FXML
    private TextField invoicePhone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTextFields();
        initRadioButtons();
    }

    public void initTextFields() {
        //sets the textfields to the same as delivery by default
        invoiceFirstName.setText(customer.getFirstName());
        invoiceLastName.setText(customer.getLastName());
        invoiceAddress.setText(customer.getAddress());
        invoicePostCode.setText(customer.getPostCode());
        invoicePostAddress.setText(customer.getPostAddress());
        invoiceEmail.setText(customer.getEmail());
        invoicePhone.setText(customer.getPhoneNumber());
    }

    public void initRadioButtons() {
        //sameAsDelivery chosen by default. otherAdress = disabeld in Scenebuilder due to time restraints
        sameAsDelivery.setSelected(true);
        sameAsDelivery.setToggleGroup(radioButtonGroup);
        otherAdress.setToggleGroup(radioButtonGroup);
    }

    //back to delivery view when clicked "go back" <--
    public void backToDeliveryClicked(ActionEvent event) throws IOException {
        viewChanger.changeScene(paymentViewInvoice, "/fxml/DeliveryView.fxml");
    }

    //gives us the confirmation view when user clicked "continue" -->
    public void continueClicked() throws IOException {
        viewChanger.changeScene(paymentViewInvoice, "/fxml/ConfirmationView.fxml");
    }
}
