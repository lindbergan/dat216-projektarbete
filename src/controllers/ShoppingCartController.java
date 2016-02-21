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



    public ShoppingCartController(){


    }
    public void cartAmountClicked(){
        cartAmount.clear();


    }
    public void switchView(){
        cartView.setVisible(false);

    }
}