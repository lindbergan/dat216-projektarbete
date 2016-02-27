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

//skriver slutligen en vy och en klass för den sista "rutan" med tack för ditt köp, fortsätt handla osv..
public class DeliveryViewController implements Initializable{

    @FXML private MenuBar menuBar;
    @FXML private AnchorPane deliveryView;
    @FXML private AnchorPane paymentViewCard;
    @FXML private AnchorPane paymentViewInvoice;
    @FXML private AnchorPane confimationView;
    @FXML private ImageView iMatLogo; //photoshop, labeled buttons



    final ToggleGroup radioButtonGroup = new ToggleGroup();
    @FXML private RadioButton cardPayment;
    @FXML private RadioButton invoicePayment;


    @Override

    //sets the radiobuttons, card payment = default choise
    public void initialize(URL url, ResourceBundle rb) {

        cardPayment.setSelected(true);
        invoicePayment.setSelected(false);
        cardPayment.setToggleGroup(radioButtonGroup);
        invoicePayment.setToggleGroup(radioButtonGroup);
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

    //gives us the right payment view
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

}
