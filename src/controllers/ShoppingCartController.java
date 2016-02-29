package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;


/**
 * Created by Razmus on 2016-02-21.
 */
public class ShoppingCartController extends IMatController {
    @FXML
    private TextField cartAmount;
    @FXML
    private Label itemName;
    @FXML
    private Button decItem;
    @FXML
    private Button incItem;
    @FXML
    private Label price;
    @FXML
    private AnchorPane cartPane;
    private Stage checkoutStage;



    public void preventNull() {
        if (!cartAmount.isFocused() && cartAmount.getText() == null) {
            cartAmount.setText("1");
        }
    }

    public void cartAmountClicked() {
        cartAmount.setText(null);


    }

    public void incItem() {
        if (cartAmount.getText() != null) {
            int old = Integer.parseInt(cartAmount.getText());
            int tmp = old + 1;
            String updated = "" + tmp;
            cartAmount.setText(updated);
        } else cartAmount.setText("1");
    }

    public void decItem() {
        if (cartAmount.getText() != null) {
            int old = Integer.parseInt(cartAmount.getText());
            if (old != 0 && old != 1) {
                int tmp = old - 1;
                String updated = "" + tmp;
                cartAmount.setText(updated);
            }
        } else cartAmount.setText("1");
    }

    public void preventZero() {
        int old = Integer.parseInt(cartAmount.getText());
        if (old == 0) {
            cartAmount.setText("1");
        }
    }

    public void deleteItem() {
        preventNull();
        itemName.setVisible(false);
        cartAmount.setVisible(false);
        incItem.setVisible(false);
        decItem.setVisible(false);
        price.setVisible(false);
    }

    public void setPane()throws IOException{
        AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/categoryMenu.fxml"));
        cartPane.getChildren().setAll(e);

    }


    //added in order to go to checkoutview
    public void goToCheckout(ActionEvent event) throws IOException{
        Parent checkoutParent = FXMLLoader.load(getClass().getResource("/fxml/CheckoutView.fxml"));
        Scene checkoutScene = new Scene(checkoutParent);
        Stage checkoutStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        checkoutStage.hide();
        checkoutStage.setScene(checkoutScene);
        checkoutStage.show();
    }


}



/* ursprungliga:

    public void goToCheckout(ActionEvent event) throws IOException{
        Parent helpParent = FXMLLoader.load(getClass().getResource("/fxml/CheckoutView.fxml"));
        Scene helpScene = new Scene(helpParent);
        helpStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        helpStage.hide();
        helpStage.setScene(helpScene);
        helpStage.show();
    }
 */

