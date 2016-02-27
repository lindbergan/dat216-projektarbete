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

public class InvoiceController implements Initializable {


    @FXML private AnchorPane paymentViewInvoice;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton sameAsDelivery;
    @FXML private RadioButton otherAdress;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();


    @FXML private TextField invoiceFirstName;
    @FXML private TextField invoiceLastName;
    @FXML private TextField invoiceAddress;
    @FXML private TextField invoicePostCode;
    @FXML private TextField invoicePostAddress;
    @FXML private TextField invoiceEmail;
    @FXML private TextField invoicePhone;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //sets the radiobuttons, visa = default choise, user specified option is set to diabled due to time restraints
        sameAsDelivery.setSelected(true);
        sameAsDelivery.setToggleGroup(radioButtonGroup);
        otherAdress.setToggleGroup(radioButtonGroup);

        //sets the textfields to the same as delivery by default
        invoiceFirstName.setText(customer.getFirstName());
        invoiceLastName.setText(customer.getLastName());
        invoiceAddress.setText(customer.getAddress());
        invoicePostCode.setText(customer.getPostCode());
        invoicePostAddress.setText(customer.getPostAddress());
        invoiceEmail.setText(customer.getEmail());
        invoicePhone.setText(customer.getPhoneNumber());
    }


    //back to delivery view when clicked <--
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
        paymentViewInvoice.getChildren().setAll(confirmationView);

    }

}
