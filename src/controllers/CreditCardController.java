package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

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
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreditCardController implements Initializable {

    private static boolean visited = false;
    final ToggleGroup radioButtonGroup = new ToggleGroup();
    IMatDataHandler handler = IMatDataHandler.getInstance();
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
    private ObservableList<String> cardYear = FXCollections.observableArrayList("16", "17", "18", "19", "20", "21", "22");
    private ObservableList<String> cardMonth = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    private CreditCard creditCard = handler.getCreditCard();
    private ViewChanger viewChanger = new ViewChanger();

    public static boolean hasBeenVisited() {
        return visited;
    }

    @Override
    //sets the radiobuttons, visa = default choise
    public void initialize(URL url, ResourceBundle rb) {

        //makes sure that it has been visited in order to be able to jump pass it from the buttons in the header
        visited = true;

        //initialize the fields
        initTextFields();
        initiChoiseBoxes();
        initRadioButtons();

        //listen to all the fields user can change
        this.listenToTextField();
        this.listenToChoiseboxes();
    }

    public void initTextFields() {
        cardHolderName.setText(creditCard.getHoldersName());
        creditCardNumbr.setText(creditCard.getCardNumber());
        cvv.setText("" + creditCard.getVerificationCode()); //dont know why i cant reach toString()
    }

    public void initiChoiseBoxes() {
        cardYearChoiseBox.setItems(cardYear);
        cardMonthChoiseBox.setItems(cardMonth);

        //get the value from previous session
        cardYearChoiseBox.getSelectionModel().select(creditCard.getValidYear());
        cardMonthChoiseBox.getSelectionModel().select(creditCard.getValidMonth());
    }

    public void initRadioButtons() {
        visa.setToggleGroup(radioButtonGroup);
        mastercard.setToggleGroup(radioButtonGroup);
        other.setToggleGroup(radioButtonGroup);

        //sets the radioButtons to default (visa) or previously specified type
        setDefaultRadioButton();
    }

    public void setDefaultRadioButton() {

        if (creditCard.getCardType() == "mastercard") {
            mastercard.isSelected();
        } else if (creditCard.getCardType() == "other") {
            other.isSelected();
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
                creditCard.setValidYear(newValue.intValue());
            }
        });
    }

    //back to deliveryView when clicked "go back" <--
    public void backToDeliveryClicked(ActionEvent event) throws IOException {

        viewChanger.changeScene(paymentViewCard, "/fxml/DeliveryView.fxml");
        /*
        AnchorPane deliveryView = FXMLLoader.load(getClass().getResource("/fxml/DeliveryView.fxml"));
        paymentViewCard.getChildren().setAll(deliveryView);
*/
    }

    //gives us the confirmation view when clicked "continue" -->
    public void continueClicked() throws IOException {

        creditCard.setCardType(radioButtonGroup.getSelectedToggle().toString());
        //sends the radiobutton info to iMatHandler

        /*
        if(visa.isSelected()){
            creditCard.setCardType("visa");
        }
        else if(mastercard.isSelected()){
            creditCard.setCardType("mastercard");
        }
        else{
            creditCard.setCardType("other");
        }


        */
        viewChanger.changeScene(paymentViewCard, "/fxml/ConfirmationView.fxml");

        /*
        AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml"));
        paymentViewCard.getChildren().setAll(confirmationView);
        */
    }
}
