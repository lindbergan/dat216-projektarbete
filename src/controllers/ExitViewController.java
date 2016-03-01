package controllers;

/**
 * Created by Jolo on 2/26/16.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExitViewController implements Initializable {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    private Customer customer = handler.getCustomer();
    @FXML private AnchorPane exitView;
    @FXML private Label customerEmail;
    @FXML private Label customerDate;
    @FXML private Button receipts;
    private ViewChanger viewChanger = new ViewChanger();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initTextFields();
    }

    public void initTextFields(){

        customerEmail.setText(customer.getEmail());
        customerDate.setText(DeliveryViewController.getUserSpecifiedDate() + " " +
                DeliveryViewController.getUserSpecifiedMonth() + " mellan kl " +
                DeliveryViewController.getUserSpecifiedMinTime() + " - " +
                DeliveryViewController.getUserSpecifiedMaxTime());
    }

    //goes back to the store when "continue shopping" button is pushed
    public void continueShopping(ActionEvent event) throws IOException{

        viewChanger.changeStage(event,exitView, "/fxml/IMat.fxml");
        /*
        Parent imatParent = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));
        Scene imatScene = new Scene(imatParent, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
        Stage imatStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        imatStage.hide();
        imatStage.setScene(imatScene);
        imatStage.show();
        */

    }

    //goes back to iMatView and shows receipts
    public void receiptsButtonPushed(ActionEvent event){
        //byt stage till kvitto-stage:n
    }

    //closes the application and saves user settings
    public void closeWindow(ActionEvent event){
        handler.shutDown();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
