package controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import properties.StringComparer;
import properties.ViewChanger;
import properties.ViewSingelton;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InvoiceController implements Initializable {

    final ToggleGroup radioButtonGroup = new ToggleGroup();
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private ViewChanger viewChanger = new ViewChanger();
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
    @FXML private Label infoLabel;
    @FXML private Button continueButton;
    ViewSingelton currentView = ViewSingelton.getInstance();
    private static String invoiceDeliveryAdress = "Samma som leveransadress";
    private static String userInputFirstName;
    private static String userInputLastName;
    private static String userInputAdress;
    private static String userInputPostCode;
    private static String userInputPostAdress;
    private static String userInputEmail;
    private static String userInputPhone;
    private static boolean allFieldsFilled = true; //default

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initRadioButtons();
        listenToRadioButtons();
        initTextFields();
        listenToTextFields();

    }

    public void initRadioButtons() {
        //sameAsDelivery chosen by default
        sameAsDelivery.setToggleGroup(radioButtonGroup);
        otherAdress.setToggleGroup(radioButtonGroup);

        if(invoiceDeliveryAdress == "Samma som leveransadress") {
            sameAsDelivery.setSelected(true);
        }
        else{
            otherAdress.setSelected(true);
        }
    }

    public void listenToRadioButtons() {

        //to detemine which radiobutton was chosen
        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (newValue == sameAsDelivery) {
                    invoiceDeliveryAdress = "Samma som leveransadress";
                    initTextFields();}
                else {
                    invoiceDeliveryAdress = "Annan adress";
                    setAllFieldsDisabled(false);
                    setDeliveryAsDefault();
                }
            }
        });
    }

    public void setDeliveryAsDefault(){
        userInputFirstName = customer.getFirstName();
        userInputLastName = customer.getLastName();
        userInputAdress = customer.getAddress();
        userInputPostCode = customer.getPostCode();
        userInputPostAdress = customer.getPostAddress();
        userInputEmail = customer.getEmail();
        userInputPhone = customer.getPhoneNumber();
    }

    public void initTextFields() {

        if(sameAsDelivery.isSelected()) {
            //sets the textfields to the same as delivery by default
            invoiceFirstName.setText(customer.getFirstName());
            invoiceLastName.setText(customer.getLastName());
            invoiceAddress.setText(customer.getAddress());
            invoicePostCode.setText(customer.getPostCode());
            invoicePostAddress.setText(customer.getPostAddress());
            invoiceEmail.setText(customer.getEmail());
            invoicePhone.setText(customer.getPhoneNumber());
            setAllFieldsDisabled(true);

        }
        else{
            invoiceFirstName.setText(userInputFirstName);
            invoiceLastName.setText(userInputLastName);
            invoiceAddress.setText(userInputAdress);
            invoicePostCode.setText(userInputPostCode);
            invoicePostAddress.setText(userInputPostAdress);
            invoiceEmail.setText(userInputEmail);
            invoicePhone.setText(userInputPhone);
            setAllFieldsDisabled(false);
        }
    }

    public void setAllFieldsDisabled(boolean bol){
        invoiceFirstName.setDisable(bol);
        invoiceLastName.setDisable(bol);
        invoiceAddress.setDisable(bol);
        invoicePostCode.setDisable(bol);
        invoicePostAddress.setDisable(bol);
        invoiceEmail.setDisable(bol);
        invoicePhone.setDisable(bol);
    }

    //ChangeListener for the textfields:
    public void listenToTextFields() {

        //firstName:
        invoiceFirstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)){
                    userInputFirstName = newValue;
                }
                else {
                    invoiceFirstName.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //LastName:
        invoiceLastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)){
                    userInputLastName = newValue;
                }
                else {
                    invoiceLastName.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //Adress:
        invoiceAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                userInputAdress = newValue;
                checkIfAllFieldsFilledIn();
            }
        });

        //PostCode:
        invoicePostCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsNumbers(newValue) && newValue.length()<6){
                    userInputPostCode = newValue;
                }
                else {
                    invoicePostCode.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //PostAdress:
        invoicePostAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)){
                    userInputPostAdress = newValue;
                }
                else {
                    invoicePostAddress.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //Email:
        invoiceEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                userInputEmail = newValue;
                checkIfAllFieldsFilledIn();
            }
        });

        //PhoneNumber:
        invoicePhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsNumbers(newValue) && newValue.length()<11){
                    userInputPhone = newValue;
                }
                else {
                    invoicePhone.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });
    }

    //back to delivery view when clicked "go back" <--
    public void backToDeliveryClicked(ActionEvent event) throws IOException {
        DataHolder.deliveryViewController.setDeliveryProgImage();
        viewChanger.changeScene(paymentViewInvoice, "/fxml/DeliveryView.fxml");
        currentView.setCurrentViewName("deliveryView");
    }

    //gives us the confirmation view when user clicked "continue" -->
    public void continueClicked() throws IOException {
        DataHolder.deliveryViewController.setConfirmationProgImage();

        viewChanger.changeScene(paymentViewInvoice, "/fxml/ConfirmationView.fxml");
        currentView.setCurrentViewName("confirmationView");
    }

    public void checkIfAllFieldsFilledIn() {

        if(invoiceFirstName != null && !invoiceFirstName.getText().isEmpty() && invoiceLastName!= null
                && !invoiceLastName.getText().isEmpty() && invoiceAddress != null &&
                !invoiceAddress.getText().isEmpty() && invoicePostCode != null &&
                !invoicePostCode.getText().isEmpty()  && invoicePhone!= null && !invoicePhone.getText().isEmpty()
                && invoiceEmail!= null && !invoiceEmail.getText().isEmpty()){

            allFieldsFilled = true;
            continueButton.setDisable(false);
            continueButton.setCursor(Cursor.HAND);
            infoLabel.setVisible(false);
        }

        else {
            allFieldsFilled = false;
            continueButton.setDisable(true);
            continueButton.setCursor(Cursor.DEFAULT);
            infoLabel.setVisible(true);
        }
    }

    public static boolean AllFieldsFilled(){
        return allFieldsFilled;
    }
}
