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
    private static String paymentChoise = "Kortbetalning"; //default set to card
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    IMatDataHandler handler = IMatDataHandler.getInstance();
    @FXML
    private AnchorPane deliveryView;
    private Customer customer = handler.getCustomer();
    private CreditCard creditCard = handler.getCreditCard();
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
    private Button deliveryButton;
    @FXML
    private Button paymentButton;
    @FXML
    private Button confirmationButton;
    @FXML
    private ChoiceBox monthChoisebox;
    @FXML
    private ChoiceBox dateChoisebox;
    @FXML
    private ChoiceBox minTimeChoisebox;
    @FXML
    private ChoiceBox maxTimeChoisebox;
    //the observable lists for the Choiseboxes
    private ObservableList<String> month = FXCollections.observableArrayList("Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December");
    private ObservableList<String> date = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
    private ObservableList<String> minTime = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24");
    private ObservableList<String> maxTime = FXCollections.observableArrayList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24");
    private boolean allFieldsFilled;
    private ViewChanger viewChanger = new ViewChanger();

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

        //listen to all the fields user can change
        listenToTextField();
        listenToChoiseboxes();
        listenToRadioButtons();
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

        viewChanger.changeStage(event, deliveryView, "/fxml/IMat.fxml");
        /*
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
        Scene mainScene = new Scene(mainParent, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Stage mainStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainStage.hide();
        mainStage.setScene(mainScene);
        mainStage.show();
        */
    }

    //back to IMat store when user clicked "back to store" <--
    public void backtoStoreClicked(ActionEvent event) throws IOException {

        viewChanger.changeStage(event, deliveryView, "/fxml/IMat.fxml");
        /*
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
        Scene mainScene = new Scene(mainParent, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.hide();
        mainStage.setScene(mainScene);
        mainStage.show();
        */
    }

    //gives us the right PaymentView depending on what radiobutton is selected
    public void continueClicked() throws IOException {

        allFieldsFilledIn();
        if (allFieldsFilled) {

            if (paymentChoise == "Kortbetalning") {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewCard.fxml");

                /*
                AnchorPane cardView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml"));
                deliveryView.getChildren().setAll(cardView);
                */
            } else {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewInvoice.fxml");

                /*
                AnchorPane invoiceView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewInvoice.fxml"));
                deliveryView.getChildren().setAll(invoiceView);
                */
            }
        }
    }

    //Associate the different buttons in the header to the corresponding View
    public void DeliveryButtonPushed() throws IOException {
        viewChanger.changeScene(deliveryView, "/fxml/DeliveryView.fxml");

        /*
        AnchorPane delView = FXMLLoader.load(getClass().getResource("/fxml/DeliveryView.fxml"));
            deliveryView.getChildren().setAll(delView);
            */
    }

    public void PaymentButtonPushed() throws IOException {

        allFieldsFilledIn();
        if (allFieldsFilled) {

            if (paymentChoise == "Kortbetalning") {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewCard.fxml");
                /*
                AnchorPane cardView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml"));
                deliveryView.getChildren().setAll(cardView);
                */
            } else {
                viewChanger.changeScene(deliveryView, "/fxml/PaymentViewInvoice.fxml");

                /*
                AnchorPane invoiceView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewInvoice.fxml"));
                deliveryView.getChildren().setAll(invoiceView);
                */
            }
        }
    }

    public void ConfirmationButtonPushed() throws IOException {

        //makes sure that the customer has been to InvoiceView or CardView before entering ConfirmationView.
        if (InvoiceController.hasBeenVisited() || CreditCardController.hasBeenVisited()) {
            viewChanger.changeScene(deliveryView, "/fxml/ConfirmationView.fxml");

            /*
            AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml"));
            deliveryView.getChildren().setAll(confirmationView);
            */
        }
    }

    public void allFieldsFilledIn() {

        /*
        if(customerFirstName != null && customerLastName!= null && customerAddress != null
                && customerPostCode != null && customerPhone!= null && customerEmail!= null
                && userSpecifiedMonth!= null && userSpecifiedDate!= null && userSpecifiedMinTime!= null
                && userSpecifiedMaxTime!= null){
            allFieldsFilled = true;
        }
        else{
            allFieldsFilled =false;
        }
        */
        //TA BORT NÄR FÄRDIGT. BARA FÖR BEKVÄMLIGHET VID TESTNING
        allFieldsFilled = true;
    }
}
