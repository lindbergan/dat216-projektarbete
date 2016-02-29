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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;


public class CreditCardController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML private AnchorPane paymentViewCard;

    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton visa;
    @FXML private RadioButton mastercard;
    @FXML private RadioButton other;
    @FXML private TextField cardHolderName;
    @FXML private TextField creditCardNumbr;
    @FXML private TextField cvv;
    @FXML private ChoiceBox cardYearChoiseBox;
    @FXML private ChoiceBox cardMonthChoiseBox;

    private ObservableList<String> cardYear = FXCollections.observableArrayList("16","17","18","19","20","21","22");
    private ObservableList<String> cardMonth = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12");

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private CreditCard creditCard = handler.getCreditCard();
    private static boolean visited = false;


    @Override
    //sets the radiobuttons, visa = default choise
    public void initialize(URL url, ResourceBundle rb) {

        //initialize the choiseboxes
        cardYearChoiseBox.setItems(cardYear);
        cardMonthChoiseBox.setItems(cardMonth);

        //sets the radioButtons to groups
        visa.setToggleGroup(radioButtonGroup);
        mastercard.setToggleGroup(radioButtonGroup);
        other.setToggleGroup(radioButtonGroup);


        //sets the radioButtons to default (visa) or previously specified type
        if(creditCard.getCardType() == "mastercard"){
            mastercard.isSelected();
        }
        else if(creditCard.getCardType()=="other"){
            other.isSelected();
        }
        else {
            visa.setSelected(true);
        }

        //sets the values from iMatHandler:
        cardHolderName.setText(creditCard.getHoldersName());
        creditCardNumbr.setText(creditCard.getCardNumber());
        cvv.setText(""+creditCard.getVerificationCode()); //dont know why i cant reach toString()
        cardYearChoiseBox.getSelectionModel().select(creditCard.getValidYear());
        cardMonthChoiseBox.getSelectionModel().select(creditCard.getValidMonth());

        //makes sure that it has been visited in order to be able to jump pass it from the buttons in the header
        visited = true;

        //listen to all the fields user can change
        this.listenToTextField();
        this.listenToChoiseboxes();
    }


    //ChangeListener for the choiseboxes
    public void listenToChoiseboxes(){

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

    //ChangeListener for the textfields:
    public void listenToTextField(){

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


    //back to deliveryView when clicked <--
    public void backToDeliveryClicked(ActionEvent event)throws IOException {

        AnchorPane deliveryView = FXMLLoader.load(getClass().getResource("/fxml/DeliveryView.fxml"));
        paymentViewCard.getChildren().setAll(deliveryView);

    }

    //gives us the confirmation view
    public void continueClicked()throws IOException{

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

        AnchorPane confirmationView = FXMLLoader.load(getClass().getResource("/fxml/ConfirmationView.fxml"));
        paymentViewCard.getChildren().setAll(confirmationView);
    }

    public static boolean hasBeenVisited(){
        return visited;
    }
}
