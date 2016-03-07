package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import properties.StringComparer;
import properties.ViewChanger;
import properties.ViewSingelton;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

//OBS: Detta är main-controllern OCH controllern för DeliveryView:n. Inte optimalt att ha dem sammanslagna, men lyckades inte ta mig runt detta.
public class DeliveryViewController implements Initializable {


    private static LocalDate userSpecifiedDate;
    private static String userSpecifiedTime;
    private static String paymentChoice = "Kortbetalning";

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    private CreditCard creditCard = handler.getCreditCard();
    private ViewChanger viewChanger = new ViewChanger();

    final ToggleGroup radioButtonGroup = new ToggleGroup();

    @FXML
    private AnchorPane deliveryView;
    @FXML
    private RadioButton cardPayment;
    @FXML
    private RadioButton invoicePayment;
    @FXML
    private TextField customerFirstName;
    @FXML
    private TextField customerLastName;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerPostCode;
    @FXML
    private TextField customerPostAddress;
    @FXML
    private TextField customerEmail;
    @FXML
    private TextField customerPhone;
    @FXML

    private ChoiceBox timeIntervallChoicebox;
    @FXML private Button continueButton;
    @FXML private Label infoLabel;
    @FXML private DatePicker calendar;

    @FXML private Button deliveryButton;
    @FXML private Button paymentButton;
    @FXML private Button confirmationButton;

    private static boolean allFieldsFilled = false;
    private static ViewSingelton currentView = ViewSingelton.getInstance();

    //the observable lists for the Choiceboxes
    private ObservableList<String> timeIntervall = FXCollections.observableArrayList("04.00 - 08.00", "05.00 - 09.00", "06.00 - 10.00",
            "07.00 - 11.00", "08.00 - 12.00", "09.00 - 13.00", "10.00 - 14.00", "11.00 - 15.00", "12.00 - 16.00", "13.00 - 17.00", "14.00 - 18.00", "15.00 - 19.00",
            "16.00 - 20.00", "17.00 - 21.00", "18.00 - 22.00", "19.00 - 23.00", "20.00 - 00.00");

    //The getters for our custom choisboxes and radiobuttons and Datepicker
    public static String getUserSpecifiedDate() {
        return userSpecifiedDate.toString();
    }
    public static String getUserSpecifiedTime() {
        return userSpecifiedTime;
    }

    public static String getPaymentChoice() {
        return paymentChoice;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initialize the fields
        initTextFields();
        initChoiceBoxes();
        initRadioButtons();
        initDatePicker();

        //listen to all the fields user can change
        listenToTextField();
        listenToChoiceboxes();
        listenToRadioButtons();
        listenToDatePicker();

        checkIfAllFieldsFilledIn();
    }

    public void initTextFields() {

        //sets the textfields to the correct default values
        customerFirstName.setText(customer.getFirstName());
        customerLastName.setText(customer.getLastName());
        customerAddress.setText(customer.getAddress());
        customerPostCode.setText(customer.getPostCode());
        customerPostAddress.setText(customer.getPostAddress());
        customerEmail.setText(customer.getEmail());
        customerPhone.setText(customer.getPhoneNumber());
    }

    public void initChoiceBoxes() {



        timeIntervallChoicebox.setItems(timeIntervall);

        //sets the choiesBoxes in case customer chose "go back" from paymentView
        userSpecifiedChoiceBox();

    }



    public void userSpecifiedChoiceBox() {

        timeIntervallChoicebox.setValue(userSpecifiedTime);
    }

    public void initRadioButtons() {

        cardPayment.setToggleGroup(radioButtonGroup);
        invoicePayment.setToggleGroup(radioButtonGroup);

        if (paymentChoice == "Kortbetalning") {
            cardPayment.setSelected(true);
            invoicePayment.setSelected(false);
        } else {
            cardPayment.setSelected(false);
            invoicePayment.setSelected(true);
        }
    }

    public void initDatePicker(){
        calendar.setValue(userSpecifiedDate);
    }

    //ChangeListener for the textfields:
    public void listenToTextField() {

        //firstName:
        customerFirstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)){
                    customer.setFirstName(newValue);
                }
                else {
                    customerFirstName.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //LastName:
        customerLastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)){
                    customer.setLastName(newValue);
                }
                else {
                    customerLastName.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //Adress:
        customerAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setAddress(newValue);
                checkIfAllFieldsFilledIn();
            }
        });

        //PostCode:
        customerPostCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsNumbers(newValue) && newValue.length()<6){
                    customer.setPostCode(newValue);
                }
                else {
                    customerPostCode.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //PostAdress:
        customerPostAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)){
                    customer.setPostAddress(newValue);
                }
                else {
                    customerPostAddress.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });

        //Email:
        customerEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setEmail(newValue);
                checkIfAllFieldsFilledIn();
            }
        });

        //PhoneNumber:
        customerPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsNumbers(newValue) && newValue.length()<11){
                    customer.setPhoneNumber(newValue);
                }
                else {
                    customerPhone.setText(oldValue);
                }
                checkIfAllFieldsFilledIn();
            }
        });
    }

    //ChangeListener for the choiceboxes
    public void listenToChoiceboxes() {

        //selected timeIntervall
        timeIntervallChoicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiceBoxIndex = newValue.intValue();
                userSpecifiedTime = timeIntervall.get(choiceBoxIndex);
                checkIfAllFieldsFilledIn();
            }
        });
    }

    public void listenToRadioButtons() {

        //to detemine which radiobutton was chosen
        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (newValue == cardPayment) {
                    paymentChoice = "Kortbetalning";
                } else {
                    paymentChoice = "Fakturabetalning";
                }
            }
        });
    }

    public void listenToDatePicker(){
        calendar.setOnAction(event -> {
            userSpecifiedDate = calendar.getValue();
            checkIfAllFieldsFilledIn();
        });
    }

    //back to IMat store when user clicked the Logotype
    public void logoClicked(ActionEvent event) throws IOException {
        viewChanger.changeStageOverride(event, "/fxml/IMat.fxml");
    }

    //back to IMat store when user clicked "back to store" <--
    public void backtoStoreClicked(ActionEvent event) throws IOException {
        viewChanger.changeStageOverride(event, "/fxml/IMat.fxml");
    }

    //gives us the right PaymentView depending on what radiobutton is selected
    public void continueClicked() throws IOException {

        currentView.setCurrentViewName("paymentView");


        if (paymentChoice == "Kortbetalning") {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewCard.fxml");
            } else {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewInvoice.fxml");
            }
    }

    public void checkIfAllFieldsFilledIn() {

        if(customerFirstName != null && !customerFirstName.getText().isEmpty() && customerLastName!= null
                && !customerLastName.getText().isEmpty() && customerAddress != null &&
                !customerAddress.getText().isEmpty() && customerPostCode != null &&
                !customerPostCode.getText().isEmpty()  && customerPhone!= null && !customerPhone.getText().isEmpty()
                && customerEmail!= null && !customerEmail.getText().isEmpty() && userSpecifiedDate !=null
                && userSpecifiedTime!= null){

            allFieldsFilled = true;
            continueButton.setDisable(false);
            infoLabel.setVisible(false);
        }

        else {
            allFieldsFilled = false;
            continueButton.setDisable(true);
            infoLabel.setVisible(true);
        }
    }

    public void deliveryButtonClicked()throws IOException{
        viewChanger.changeScene(deliveryView,"/fxml/DeliveryView.fxml" );
    }

    public void paymentButtonClicked()throws IOException{

        if(allFieldsFilled){
            continueClicked();
        }
    }

    public void confirmationButtonClicked()throws IOException{

        if(allFieldsFilled && CreditCardController.areAllFieldsFilled()){
            viewChanger.changeScene(deliveryView,"/fxml/ConfirmationView.fxml");
}    }

    //Need somehow to create a listener that can listen to changes to viewName in the class ViewSingelton.
    // String is primitive - thus, cant listen to changes. But we need to find a workaround.

    public void setCorrectHeaderButton(){
        if(currentView.getCurrentViewName() == "deliveryView"){

            System.out.println("deliveryView");
            //buttonAction

        }
        else if(currentView.getCurrentViewName() == "paymentView"){

            System.out.println("paymentView");
            //buttonAction

        }
        else{
            System.out.println("ConfirmationView");
            //buttonAction
        }
    }
}
