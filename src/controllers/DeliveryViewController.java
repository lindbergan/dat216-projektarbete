package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

public class DeliveryViewController implements Initializable{

    @FXML private AnchorPane deliveryView;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    private CreditCard creditCard = handler.getCreditCard();

    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton cardPayment;
    @FXML private RadioButton invoicePayment;

    @FXML private TextField customerFirstName;
    @FXML private TextField customerLastName;
    @FXML private TextField customerAddress;
    @FXML private TextField customerPostCode;
    @FXML private TextField customerPostAddress;
    @FXML private TextField customerEmail;
    @FXML private TextField customerPhone;

    @FXML private Button deliveryButton;
    @FXML private Button paymentButton;
    @FXML private Button confirmationButton;

    @FXML private ChoiceBox monthChoisebox;
    @FXML private ChoiceBox dateChoisebox;
    @FXML private ChoiceBox minTimeChoisebox;
    @FXML private ChoiceBox maxTimeChoisebox;

    //the observable lists for the Choiseboxes
    private ObservableList<String> month = FXCollections.observableArrayList("Januari","Februari", "Mars","April","Maj","Juni","Juli","Augusti","September","Oktober","November","December");
    private ObservableList<String> date = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
    private ObservableList<String> minTime = FXCollections.observableArrayList("00","01", "02","03","04","05","06","07","08","09","10","11", "12","13","14","15","16","17","18","19","20","21","22","23","24");
    private ObservableList<String> maxTime = FXCollections.observableArrayList("00","01", "02","03","04","05","06","07","08","09","10","11", "12","13","14","15","16","17","18","19","20","21","22","23","24");

    private static String userSpecifiedMonth;
    private static String userSpecifiedDate;
    private static String userSpecifiedMinTime;
    private static String userSpecifiedMaxTime;
    private static String paymentChoise = "Kortbetalning"; //default set to card
    private boolean allFieldsFilled;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //sets the textfields to the correct default values
        customerFirstName.setText(customer.getFirstName());
        customerLastName.setText(customer.getLastName());
        customerAddress.setText(customer.getAddress());
        customerPostCode.setText(customer.getPostCode());
        customerPostAddress.setText(customer.getPostAddress());
        customerEmail.setText(customer.getEmail());
        customerPhone.setText(customer.getPhoneNumber());

        //initialize the choiseboxes
        monthChoisebox.setItems(month);
        dateChoisebox.setItems(date);
        minTimeChoisebox.setItems(minTime);
        maxTimeChoisebox.setItems(maxTime);

        //sets the choiesBoxes in case customer chose "go back" from paymentView
        monthChoisebox.setValue(userSpecifiedMonth);
        dateChoisebox.setValue(userSpecifiedDate);
        minTimeChoisebox.setValue(userSpecifiedMinTime);
        maxTimeChoisebox.setValue(userSpecifiedMaxTime);

        //sets the radiobuttons
        if(paymentChoise == "Kortbetalning") {
            cardPayment.setSelected(true);
            invoicePayment.setSelected(false);
        }
        else{
            cardPayment.setSelected(false);
            invoicePayment.setSelected(true);
        }
        cardPayment.setToggleGroup(radioButtonGroup);
        invoicePayment.setToggleGroup(radioButtonGroup);

        //to detemine which radiobutton was chosen
        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if(newValue == cardPayment){
                    paymentChoise = "Kortbetalning";
                }
                else{
                    paymentChoise = "Fakturabetalning";
                }
            }
        });

        //listen to all the fields user can change
        this.listenToTextField();
        this.listenToChoiseboxes();
    }

    //back to IMat store when user clicked the Logotype
    public void logoClicked(ActionEvent event) throws IOException{

        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
        Scene mainScene = new Scene(mainParent, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Stage mainStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        mainStage.hide();
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    //back to IMat store when user clicked "back" <--
    public void backtoStoreClicked(ActionEvent event) throws IOException {

        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
        Scene mainScene = new Scene(mainParent, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.hide();
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    //gives us the right payment view depending on what radiobutton is selected
    public void continueClicked()throws IOException{

        allFieldsFilledIn();
        if(allFieldsFilled) {

            if (paymentChoise == "Kortbetalning") {
                AnchorPane cardView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml"));
                deliveryView.getChildren().setAll(cardView);
            } else {
                AnchorPane invoiceView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewInvoice.fxml"));
                deliveryView.getChildren().setAll(invoiceView);
            }
        }
    }

    //ChangeListener for the choiseboxes
    public void listenToChoiseboxes(){

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

    //ChangeListener for the textfields:
    public void listenToTextField(){

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

    //Associate the different buttons in the header to the corresponding View
    public void DeliveryButtonPushed()throws IOException{
            AnchorPane delView = FXMLLoader.load(getClass().getResource("/fxml/DeliveryView.fxml"));
            deliveryView.getChildren().setAll(delView);
    }
    public void PaymentButtonPushed()throws IOException{

        allFieldsFilledIn();
        if(allFieldsFilled) {

            if (paymentChoise == "Kortbetalning") {
                AnchorPane cardView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml"));
                deliveryView.getChildren().setAll(cardView);
            } else {
                AnchorPane invoiceView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewInvoice.fxml"));
                deliveryView.getChildren().setAll(invoiceView);
            }
        }
    }
    public void ConfirmationButtonPushed()throws IOException{

        //makes sure that the customer has been to InvoiceView or CardView before entering ConfirmationView.
        if(InvoiceController.hasBeenVisited() || CreditCardController.hasBeenVisited()) {
            AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml"));
            deliveryView.getChildren().setAll(confirmationView);
        }
    }


    //The getters for our custom choisboxes and radiobuttons:
    public static String getUserSpecifiedMonth(){
        return userSpecifiedMonth;
    }
    public static String getUserSpecifiedDate(){
        return userSpecifiedDate;
    }
    public static String getUserSpecifiedMinTime(){
        return userSpecifiedMinTime;
    }
    public static String getUserSpecifiedMaxTime(){
        return userSpecifiedMaxTime;
    }
    public static String getPaymentChoise(){
        return paymentChoise;
    }

    public void allFieldsFilledIn(){
        if(customerFirstName != null && customerLastName!= null && customerAddress != null
                && customerPostCode != null && customerPhone!= null && customerEmail!= null
                && userSpecifiedMonth!= null && userSpecifiedDate!= null && userSpecifiedMinTime!= null
                && userSpecifiedMaxTime!= null){
            allFieldsFilled = true;
        }
        else{
            allFieldsFilled =false;
        }
    }
}
