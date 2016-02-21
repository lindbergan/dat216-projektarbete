package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;



/**
 * Created by Razmus on 2016-02-21.
 */
public class ShoppingCartController {
    @FXML private GridPane cartPane;
    @FXML private Pane cartView;
    @FXML private TextField cartAmount;

    public void cartAmountClicked(){
        cartAmount.clear();


    }
    public void incItem(){
        int old = Integer.parseInt(cartAmount.getText());
        int tmp = old + 1;
        String updated = ""+tmp;
        cartAmount.setText(updated);
    }
    public void decItem(){
        int old = Integer.parseInt(cartAmount.getText());
        if(old != 0 && old != 1){
            int tmp = old - 1;
            String updated = ""+tmp;
            cartAmount.setText(updated);
        }
    }


}
