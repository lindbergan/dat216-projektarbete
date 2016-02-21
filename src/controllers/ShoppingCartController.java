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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by Razmus on 2016-02-21.
 */
public class ShoppingCartController {
    @FXML private GridPane cartPane;
    @FXML private Pane cartView;
    @FXML private TextField cartAmount;
    @FXML private Label itemName;
    @FXML private Button decItem;
    @FXML private Button incItem;
    @FXML private Label price;


    public void preventNull(){
        if (!cartAmount.isFocused() && cartAmount.getText() == null){
            cartAmount.setText("1");
        }
    }

    public void cartAmountClicked(){
        cartAmount.setText(null);


    }
    public void incItem(){
        if(cartAmount.getText() != null) {
            int old = Integer.parseInt(cartAmount.getText());
            int tmp = old + 1;
            String updated = "" + tmp;
            cartAmount.setText(updated);
        }
        else cartAmount.setText("1");
    }
    public void decItem(){
        if(cartAmount.getText() != null) {
            int old = Integer.parseInt(cartAmount.getText());
            if (old != 0 && old != 1) {
                int tmp = old - 1;
                String updated = "" + tmp;
                cartAmount.setText(updated);
            }
        }
        else cartAmount.setText("1");
    }
    public void preventZero(){
        int old = Integer.parseInt(cartAmount.getText());
        if(old == 0){
            cartAmount.setText("1");
        }
    }
    public void deleteItem(){
        preventNull();
        itemName.setVisible(false);
        cartAmount.setVisible(false);
        incItem.setVisible(false);
        decItem.setVisible(false);
        price.setVisible(false);
    }
    public void backToStore(ActionEvent event)throws IOException {
        Parent storeParent = FXMLLoader.load(getClass().getResource("/fxml/imat.fxml"));
        Scene storeScene = new Scene(storeParent);
        Stage storeStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        storeStage.hide();
        storeStage.setScene(storeScene);
        storeStage.show();
    }


}
