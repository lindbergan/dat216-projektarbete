package controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

//skriver slutligen en vy och en klass för den sista "rutan" med tack för ditt köp, fortsätt handla osv..
public class DeliveryViewController implements Initializable{

    @FXML private AnchorPane deliveryView;
    @FXML private ImageView iMatLogo; //photoshop, labeled buttons
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();

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

        //sets the radiobuttons, card payment = default choise
        cardPayment.setSelected(true);
        invoicePayment.setSelected(false);
        cardPayment.setToggleGroup(radioButtonGroup);
        invoicePayment.setToggleGroup(radioButtonGroup);

        this.listenToTextField();
    }




    //back to IMat store when user clicked the Logotype
    public void logoClicked(ActionEvent event) throws IOException{

            Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
            Scene mainScene = new Scene(mainParent);
            Stage mainStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            mainStage.hide();
            mainStage.setScene(mainScene);
            mainStage.show();

    }

    //back to IMat store when user clicked <--
    public void backtoStoreClicked(ActionEvent event) throws IOException {

        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.hide();
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    //gives us the right payment view depending on what radiobutton is selected
    public void continueClicked()throws IOException{

        if(cardPayment.isSelected()){
            AnchorPane cardView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewCard.fxml/"));
            deliveryView.getChildren().setAll(cardView);
        }
        else {
            AnchorPane invoiceView = FXMLLoader.load(getClass().getResource("/fxml/PaymentViewInvoice.fxml/"));
            deliveryView.getChildren().setAll(invoiceView);
        }
    }


    public void listenToTextField(){

        //ChangeListener for the textfield:

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
}
