package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreditCardController implements Initializable {

    private static boolean allFieldsFilled = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private CreditCard creditCard = handler.getCreditCard();
    private ViewChanger viewChanger = new ViewChanger();
    @FXML
    private MenuBar menuBar;
    @FXML
    private AnchorPane paymentViewCard;
    @FXML
    private RadioButton visa;
    @FXML
    private RadioButton mastercard;
    @FXML
    private RadioButton other;
    @FXML
    private TextField cardHolderName;
    @FXML
    private TextField creditCardNumbr;
    @FXML
    private TextField cvv;
    @FXML
    private ChoiceBox cardYearChoiseBox;
    @FXML
    private ChoiceBox cardMonthChoiseBox;
    @FXML
    private Button continueButton;
    private ObservableList<String> cardYear = FXCollections.observableArrayList("16", "17", "18", "19", "20", "21", "22");
    private ObservableList<String> cardMonth = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

    public static boolean getAllFieldsFilled() {
        return allFieldsFilled;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //initialize the fields
        initTextFields();
        initiChoiseBoxes();
        initRadioButtons();

        //listen to all the fields user can change
        listenToTextField();
        listenToChoiseboxes();
        listenToRadioButtons();
    }

    public void initTextFields() {
        cardHolderName.setText(creditCard.getHoldersName());
        creditCardNumbr.setText(creditCard.getCardNumber());
        cvv.setText(creditCard.getVerificationCode() + ""); //vet inte varför jag inte kan nå toString()
    }

    public void initiChoiseBoxes() {
        cardYearChoiseBox.setItems(cardYear);
        cardMonthChoiseBox.setItems(cardMonth);

        //get the value from previous session
        cardYearChoiseBox.setValue(cardYear.get(creditCard.getValidYear()));
        cardMonthChoiseBox.setValue(cardMonth.get(creditCard.getValidMonth()));
    }

    public void initRadioButtons() {
        visa.setToggleGroup(radioButtonGroup);
        mastercard.setToggleGroup(radioButtonGroup);
        other.setToggleGroup(radioButtonGroup);

        //sets the radioButtons to default (visa) or previously specified type
        setDefaultRadioButton();
    }

    public void setDefaultRadioButton() {

        if (creditCard.getCardType().contains("mastercard")) {
            mastercard.setSelected(true);
        } else if (creditCard.getCardType().contains("other")) {
            other.setSelected(true);
        } else {
            visa.setSelected(true);
        }
    }

    //ChangeListener for the textfields:
    public void listenToTextField() {

        //CreditCardNumber:
        creditCardNumbr.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditCard.setCardNumber(newValue);
            }
        });

        //CardHolder:
        cardHolderName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditCard.setHoldersName(newValue);
            }
        });

        //CVV:
        cvv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                creditCard.setVerificationCode(Integer.parseInt(newValue));
            }
        });
    }

    //ChangeListener for the choiseboxes
    public void listenToChoiseboxes() {

        //selected cardYear
        cardYearChoiseBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                creditCard.setValidYear(newValue.intValue());
            }
        });
        //selected cardMonth
        cardMonthChoiseBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                creditCard.setValidMonth(newValue.intValue());
            }
        });
    }

    public void listenToRadioButtons() {

        //to detemine which radiobutton was chosen
        radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (newValue == visa) {
                    creditCard.setCardType("visa");
                } else if (newValue == mastercard) {
                    creditCard.setCardType("mastercard");
                } else {
                    creditCard.setCardType("other");
                }
            }
        });
    }

    //back to deliveryView when clicked "go back" <--
    public void backToDeliveryClicked() throws IOException {
        viewChanger.changeScene(paymentViewCard, "/fxml/DeliveryView.fxml");
    }

    //gives us the confirmation view when clicked "continue" -->
    public void continueClicked() throws IOException {

        checkIfAllFieldsFilled();
        if (allFieldsFilled) {
            viewChanger.changeScene(paymentViewCard, "/fxml/ConfirmationView.fxml");
        }
    }

    public void checkIfAllFieldsFilled() {
        if (cardHolderName != null && !cardHolderName.getText().isEmpty() && creditCardNumbr != null &&
                !creditCardNumbr.getText().isEmpty() && cvv != null && !cvv.getText().isEmpty()
                && cardYearChoiseBox != null && cardMonthChoiseBox != null) {

            allFieldsFilled = true;
        } else {
            allFieldsFilled = false;
        }
    }
}
