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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//OBS: Detta är main-controllern OCH controllern för DeliveryView:n. Inte optimalt att ha dem sammanslagna, men lyckades inte ta mig runt detta.
public class DeliveryViewController implements Initializable {


    private static String userSpecifiedMonth;
    private static String userSpecifiedDate;
    private static String userSpecifiedMinTime;
    private static String userSpecifiedMaxTime;
    private static String paymentChoise = "Kortbetalning";
    private static boolean allFieldsFilled = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    final ToggleGroup headerButtonGroup = new ToggleGroup();
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    private CreditCard creditCard = handler.getCreditCard();
    private ViewChanger viewChanger = new ViewChanger();
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
    private ChoiceBox monthChoisebox;
    @FXML
    private ChoiceBox dateChoisebox;
    @FXML
    private ChoiceBox minTimeChoisebox;
    @FXML
    private ChoiceBox maxTimeChoisebox;
    @FXML
    private ToggleButton deliveryButton;
    @FXML
    private ToggleButton paymentButton;
    @FXML
    private ToggleButton confirmationButton;
    //the observable lists for the Choiseboxes
    private ObservableList<String> month = FXCollections.observableArrayList("Januari", "Februari", "Mars",
            "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December");
    private ObservableList<String> date = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24",
            "25", "26", "27", "28", "29", "30", "31");
    private ObservableList<String> minTime = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24");
    private ObservableList<String> maxTime = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05",
            "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24");
    private boolean firstTimeRun = true;


    //The getters for our custom choisboxes and radiobuttons:
    public static String getUserSpecifiedMonth() {
        return userSpecifiedMonth;
    }

    public static String getUserSpecifiedDate() {
        return userSpecifiedDate;
    }

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
        //initToggleButtons();

        //listen to all the fields user can change
        listenToTextField();
        listenToChoiseboxes();
        listenToRadioButtons();
        listenToToggleButtons();
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

        monthChoisebox.setItems(month);
        dateChoisebox.setItems(date);
        minTimeChoisebox.setItems(minTime);
        maxTimeChoisebox.setItems(maxTime);

        //sets the choiesBoxes in case customer chose "go back" from paymentView
        userSpecifiedChoiseBox();

    }

    public void userSpecifiedChoiseBox() {

        monthChoisebox.setValue(userSpecifiedMonth);
        dateChoisebox.setValue(userSpecifiedDate);
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

    //sets the ToggleButtons to the same group and activates the default button (i.e "delivery")
    public void initToggleButtons() {

        /*
        if(firstTimeRun) {
            deliveryButton.setToggleGroup(headerButtonGroup);
            paymentButton.setToggleGroup(headerButtonGroup);
            confirmationButton.setToggleGroup(headerButtonGroup);
            firstTimeRun=false;
        }
        */
        //set the default
        //deliveryButton.setSelected(true);
    }

    //Changelisteners for the ToggleButtons
    public void listenToToggleButtons() {

        headerButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            }
        });
    }

    //ChangeListener for the textfields:
    public void listenToTextField() {

        //firstName:
        customerFirstName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setFirstName(newValue);
            }
        });

        //LastName:
        customerLastName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setLastName(newValue);
            }
        });

        //Adress:
        customerAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setAddress(newValue);
            }
        });

        //PostCode:
        customerPostCode.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setPostCode(newValue);
            }
        });

        //PostAdress:
        customerPostAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setPostAddress(newValue);
            }
        });

        //Email:
        customerEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setEmail(newValue);
            }
        });

        //PhoneNumber:
        customerPhone.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                customer.setPhoneNumber(newValue);
            }
        });
    }

    //ChangeListener for the choiseboxes
    public void listenToChoiseboxes() {

        //selected month
        monthChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiseBoxIndex = newValue.intValue();
                userSpecifiedMonth = month.get(choiseBoxIndex);
            }
        });
        //selected date
        dateChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiseBoxIndex = newValue.intValue();
                userSpecifiedDate = date.get(choiseBoxIndex);
            }
        });
        //selected minTime
        minTimeChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiseBoxIndex = newValue.intValue();
                userSpecifiedMinTime = minTime.get(choiseBoxIndex);
            }
        });
        //selected maxTime
        maxTimeChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int choiseBoxIndex = newValue.intValue();
                userSpecifiedMaxTime = maxTime.get(choiseBoxIndex);
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

        checkIfAllFieldsFilledIn();
        if (allFieldsFilled) {

            if (paymentChoise == "Kortbetalning") {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewCard.fxml");
            } else {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewInvoice.fxml");
            }
        }
    }


/*
    //--------------------kanske att allt detta kan tas bort beroende på hur jag tänker göra med knapparna i headern------------------------------

    //Associate the different buttons in the header to the corresponding View
    public void DeliveryButtonPushed() throws IOException {
        viewChanger.changeScene(deliveryView, "/fxml/DeliveryView.fxml");
    }

    public void PaymentButtonPushed() throws IOException {

        continueClicked();

        allFieldsFilledIn();
        if (allFieldsFilled) {

            if (paymentChoise == "Kortbetalning") {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewCard.fxml");
            }
            else {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewInvoice.fxml");
            }
        }
    }

    public void ConfirmationButtonPushed() throws IOException {
        //makes sure that the customer has been to InvoiceView or CardView before entering ConfirmationView.
            viewChanger.changeScene(deliveryView, "/fxml/ConfirmationView.fxml");
    }

    //------------------------------------------------------------------------------------

    */

    public void checkIfAllFieldsFilledIn() {

        if (customerFirstName != null && !customerFirstName.getText().isEmpty() && customerLastName != null
                && !customerLastName.getText().isEmpty() && customerAddress != null &&
                !customerAddress.getText().isEmpty() && customerPostCode != null &&
                !customerPostCode.getText().isEmpty() && customerPhone != null && !customerPhone.getText().isEmpty()
                && customerEmail != null && !customerEmail.getText().isEmpty()
                && userSpecifiedMonth != null && userSpecifiedDate != null && userSpecifiedMinTime != null
                && userSpecifiedMaxTime != null) {

            allFieldsFilled = true;
        } else {
            allFieldsFilled = false;
        }
    }
}
