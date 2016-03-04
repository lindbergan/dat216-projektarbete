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
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

//OBS: Detta är main-controllern OCH controllern för DeliveryView:n. Inte optimalt att ha dem sammanslagna, men lyckades inte ta mig runt detta.
public class DeliveryViewController implements Initializable {


    private static String userSpecifiedMonth;
    private static String userSpecifiedDate;
    private static String userSpecifiedMinTime;
    private static String userSpecifiedMaxTime;
    private static String paymentChoise = "Kortbetalning";

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
    private ChoiceBox minTimeChoisebox;
    @FXML
    private ChoiceBox maxTimeChoisebox;
    @FXML private Button continueButton;
    @FXML private Label infoLabel;
    @FXML private DatePicker calendar;
    private static LocalDate userDate;
    private static boolean allFieldsFilled = false;


    //the observable lists for the Choiseboxes
    private ObservableList<String> minTime = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24");
    private ObservableList<String> maxTime = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24");


    //The getters for our custom choisboxes and radiobuttons and Datepicker
    public static String getUserSpecifiedDate() {
        return userDate.toString();
    } //måste fixa denna så den retunerar ex en string

    //public static String getUserSpecifiedMonth(){ return userSpecifiedMonth;}
    public static String getUserSpecifiedMinTime() {
        return userSpecifiedMinTime;
    }

    public static String getUserSpecifiedMaxTime() {
        return userSpecifiedMaxTime;
    }

    public static String getPaymentChoise() {
        return paymentChoise;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initialize the fields
        initTextFields();
        initChoiseBoxes();
        initRadioButtons();
        initDatePicker();

        //listen to all the fields user can change
        listenToTextField();
        listenToChoiseboxes();
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

    public void initChoiseBoxes() {


        minTimeChoisebox.setItems(minTime);
        maxTimeChoisebox.setItems(maxTime);

        //sets the choiesBoxes in case customer chose "go back" from paymentView
        userSpecifiedChoiseBox();

    }


    public void userSpecifiedChoiseBox() {

        minTimeChoisebox.setValue(userSpecifiedMinTime);
        maxTimeChoisebox.setValue(userSpecifiedMaxTime);
    }

    public void initRadioButtons() {

        cardPayment.setToggleGroup(radioButtonGroup);
        invoicePayment.setToggleGroup(radioButtonGroup);

        if (paymentChoise == "Kortbetalning") {
            cardPayment.setSelected(true);
            invoicePayment.setSelected(false);
        } else {
            cardPayment.setSelected(false);
            invoicePayment.setSelected(true);
        }
    }

    public void initDatePicker(){
        calendar.setValue(userDate);
    }

    //ChangeListener for the textfields:
    public void listenToTextField() {

        //firstName:
        customerFirstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setFirstName(newValue);
                checkIfAllFieldsFilledIn();
            }
        });

        //LastName:
        customerLastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setLastName(newValue);
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
                customer.setPostCode(newValue);
                checkIfAllFieldsFilledIn();
            }
        });

        //PostAdress:
        customerPostAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setPostAddress(newValue);
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
                customer.setPhoneNumber(newValue);
                checkIfAllFieldsFilledIn();
            }
        });
    }

    //ChangeListener for the choiseboxes
    public void listenToChoiseboxes() {

        //selected minTime
        minTimeChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiseBoxIndex = newValue.intValue();
                userSpecifiedMinTime = minTime.get(choiseBoxIndex);
                checkIfAllFieldsFilledIn();
            }
        });
        //selected maxTime
        maxTimeChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiseBoxIndex = newValue.intValue();
                userSpecifiedMaxTime = maxTime.get(choiseBoxIndex);
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
                    paymentChoise = "Kortbetalning";
                } else {
                    paymentChoise = "Fakturabetalning";
                }
            }
        });
    }

    public void listenToDatePicker(){
        calendar.setOnAction(event -> {
            userDate = calendar.getValue();
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

            if (paymentChoise == "Kortbetalning") {
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
                && customerEmail!= null && !customerEmail.getText().isEmpty() && userDate !=null
                && userSpecifiedMinTime!= null && userSpecifiedMaxTime!= null){

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
}
