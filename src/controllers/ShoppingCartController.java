package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;


import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Created by Razmus on 2016-02-21.
 */
public class ShoppingCartController implements Initializable {
    IMatDataHandler handler = IMatDataHandler.getInstance();
    ShoppingCart cart = handler.getShoppingCart();

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
    @FXML private Label itemUnit;
    @FXML private GridPane grid;
    @FXML private ScrollPane scroll;
    @FXML private Label totalPrice;
    @FXML private Button delButton;

    public void preventNull() {
       /* if (!cartAmount.isFocused() && (cartAmount.getText().isEmpty() || cartAmount.getText().equals("0"))) {
            cartAmount.setText("1.0");
        }*/
    }

    public void cartAmountClicked() {
        cartAmount.selectAll();


    }

    public void incItem() {
        if (!cartAmount.getText().isEmpty()) {
            double old = Double.parseDouble(cartAmount.getText());
            double tmp = old + 1;
            String updated = "" + tmp;
            cartAmount.setText(updated);
        } else cartAmount.setText("1.0");
    }

    public void decItem() {
        if (!cartAmount.getText().isEmpty()) {
            double old = Double.parseDouble(cartAmount.getText());
            if (old != 0 && old != 1) {
                double tmp = old - 1;
                String updated = "" + tmp;
                cartAmount.setText(updated);
            }
        } else cartAmount.setText("1");
    }


    public void deleteItem(ActionEvent e) {
        handler.getShoppingCart().getItems().remove(0);
    }

    public void setPane()throws IOException{
        AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/categoryMenu.fxml/"));
        cartPane.getChildren().setAll(e);

    }
    public ShoppingItem showItem(int i){
        return handler.getShoppingCart().getItems().get(i);
    }
    public void testAddItem(){
        Product p = handler.getProduct(1);
        cart.addItem(new ShoppingItem(p,2));
        cart.addItem(new ShoppingItem(handler.getProduct(3),5));
        cart.addItem(new ShoppingItem(handler.getProduct(4),5));
        cart.addItem(new ShoppingItem(handler.getProduct(6),5));
        cart.addItem(new ShoppingItem(handler.getProduct(29),5));
        cart.addItem(new ShoppingItem(handler.getProduct(99),5));
        cart.addItem(new ShoppingItem(handler.getProduct(12),5));


    }
    //ändra sökväg till er customer.txt fil, ändra den så den har fälten name =, adress=, samt city= på var sin rad, tryck sedan på till kassan
    public void testFile(){
        try {
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("C:\\Users\\Razmus\\.dat215\\imat\\customer.txt");
            prop.load(in);

            FileOutputStream out = new FileOutputStream("C:\\Users\\Razmus\\.dat215\\imat\\customer.txt");
            String hej = itemName.getText();
            prop.setProperty("name", hej);
            prop.setProperty("adress", hej);
            prop.setProperty("city", hej);
            prop.store(out, null);
            price.setText(prop.getProperty("name"));


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        delButton.setVisible(false);
        if(handler.getShoppingCart().getItems().size() > 5){
            scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        }
        for(int i = 0; i<handler.getShoppingCart().getItems().size(); i++){
            grid.add(new Text(showItem(i).getProduct().getName()),0,i);
            TextField temp = new TextField();
            temp.setText("" + showItem(i).getAmount());
            temp.setMaxSize(59,31);
            grid.add(temp,1,i);
            Text suffix = new Text(showItem(i).getProduct().getUnitSuffix());
            grid.add(suffix,2,i);
            grid.add(new Text("" + showItem(i).getProduct().getPrice() * showItem(i).getAmount()),3,i);
            totalPrice.setText(handler.getShoppingCart().getTotal() + " :-");
            delButton = new Button("Ta bort");
            delButton.setOnAction(this::deleteItem);
            grid.add(delButton,4,i);
        }
    }
}





