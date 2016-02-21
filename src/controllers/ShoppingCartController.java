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


}
