package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;


import java.io.*;
import java.util.List;
import java.util.Properties;


/**
 * Created by Razmus on 2016-02-21.
 */
public class ShoppingCartController extends IMatController {
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
        AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/categoryMenu.fxml/"));
        cartPane.getChildren().setAll(e);

    }
    public ShoppingItem showItem(int i){
        return handler.getShoppingCart().getItems().get(i);
    }
    public void testAddItem(){
        Product p = handler.getProduct(1);
        cart.addItem(new ShoppingItem(p,2));
        itemName.setText(showItem(0).getProduct().getName());
        cartAmount.setText(""+showItem(0).getAmount());
        price.setText(""+showItem(0).getProduct().getPrice() * showItem(0).getAmount());
        itemUnit.setText(showItem(0).getProduct().getUnitSuffix());

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



}





