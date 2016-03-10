package controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import properties.StringComparer;
import properties.ViewChanger;
import properties.ViewSingelton;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreditCardController implements Initializable {

    private static boolean allFieldsFilled = false;
    //private static boolean allFieldsFilled = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    IMatDataHandler handler = IMatDataHandler.getInstance();
    ViewSingelton currentView = ViewSingelton.getInstance();
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
    private ChoiceBox cardYearChoiceBox;
    @FXML

    private ChoiceBox cardMonthChoiceBox;
    @FXML
    private Button continueButton;
    @FXML
    private Label infoLabel;
    @FXML
    private Button helpButton;
    @FXML
    private Label cvvLabel;
    private ObservableList<String> cardYear = FXCollections.observableArrayList("16", "17", "18", "19", "20", "21", "22");
    private ObservableList<String> cardMonth = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    private boolean wasClickedBefore = false;

    public static boolean AllFieldsFilled() {
        return allFieldsFilled;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        continueButton.setStyle("-fx-background-color:#A9D990");
        //initialize the fields
        initTextFields();
        initiChoiceBoxes();
        initRadioButtons();

        //listen to all the fields user can change
        listenToTextField();
        listenToChoiceboxes();
        listenToRadioButtons();

        checkIfAllFieldsFilled();
    }

    public void initTextFields() {
        cardHolderName.setText(creditCard.getHoldersName());
        creditCardNumbr.setText(creditCard.getCardNumber());
        cvv.setText(creditCard.getVerificationCode() + ""); //vet inte varför jag inte kan nå toString()
    }

    public void initiChoiceBoxes() {
        cardYearChoiceBox.setItems(cardYear);
        cardMonthChoiceBox.setItems(cardMonth);

        //get the value from previous session
        cardYearChoiceBox.setValue(cardYear.get(creditCard.getValidYear()));
        cardMonthChoiceBox.setValue(cardMonth.get(creditCard.getValidMonth()));
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

                if (StringComparer.onlyContainsNumbers(newValue) && newValue.length() < 17) {
                    creditCard.setCardNumber(newValue);
                    creditCardNumbr.setText(newValue);
                } else {
                    creditCardNumbr.setText(oldValue);
                }
                checkIfAllFieldsFilled();

            }
        });

        //CardHolder:
        cardHolderName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsLetter(newValue)) {
                    creditCard.setHoldersName(newValue);
                } else {
                    cardHolderName.setText(oldValue);
                }
                checkIfAllFieldsFilled();
            }
        });

        //CVV:
        cvv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (StringComparer.onlyContainsNumbers(newValue) && newValue.length() < 4) {
                    creditCard.setVerificationCode(Integer.parseInt(newValue));
                    cvv.setText(newValue);
                } else {
                    cvv.setText(oldValue);
                }
                checkIfAllFieldsFilled();

            }
        });
    }

    //ChangeListener for the choiceboxes
    public void listenToChoiceboxes() {

        //selected cardYear
        cardYearChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                creditCard.setValidYear(newValue.intValue());
                checkIfAllFieldsFilled();
            }
        });
        //selected cardMonth
        cardMonthChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                creditCard.setValidMonth(newValue.intValue());
                checkIfAllFieldsFilled();
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
        DataHolder.deliveryViewController.setDeliveryProgImage();
        viewChanger.changeScene(paymentViewCard, "/fxml/DeliveryView.fxml");
    }

    //gives us the confirmation view when clicked "continue" -->
    public void continueClicked() throws IOException {
        DataHolder.deliveryViewController.setConfirmationProgImage();
        viewChanger.changeScene(paymentViewCard, "/fxml/ConfirmationView.fxml");
    }

    public void checkIfAllFieldsFilled() {
        if (cardHolderName != null && !cardHolderName.getText().isEmpty() && creditCardNumbr != null &&
                !creditCardNumbr.getText().isEmpty() && cvv != null && !cvv.getText().isEmpty()
                && cardYearChoiceBox != null && cardMonthChoiceBox != null) {

            allFieldsFilled = true;
            continueButton.setDisable(false);
            continueButton.setCursor(Cursor.HAND);
            infoLabel.setVisible(false);
        } else {
            allFieldsFilled = false;
            continueButton.setDisable(true);
            continueButton.setCursor(Cursor.DEFAULT);
            infoLabel.setVisible(true);
        }
    }

    public void ifHelpButtonClicked() {

        if (wasClickedBefore) {
            helpButton.setDefaultButton(false);
            cvvLabel.setVisible(false);
            wasClickedBefore = false;
        } else {
            helpButton.setDefaultButton(true);
            cvvLabel.setVisible(true);
            wasClickedBefore = true;
        }
    }
}
