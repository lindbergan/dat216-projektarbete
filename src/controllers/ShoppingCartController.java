package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import properties.CartTextField;
import properties.DelButton;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private AnchorPane cartPane;
    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label totalPrice;
    @FXML
    private DelButton delButton;
    private ViewChanger viewChanger = new ViewChanger();


    public void amountClicked(MouseEvent e) {
        TextField temp = (TextField) e.getSource();
        temp.selectAll();
    }

  /*  public void incItem() {
        if (!cartAmount.getText().isEmpty()) {
            double old = Double.parseDouble(cartAmount.getText());
            double tmp = old + 1;
            String updated = "" + tmp;
            cartAmount.setText(updated);
        } else cartAmount.setText("1.0");
    }*/

   /* public void decItem() {
        if (!cartAmount.getText().isEmpty()) {
            double old = Double.parseDouble(cartAmount.getText());
            if (old != 0 && old != 1) {
                double tmp = old - 1;
                String updated = "" + tmp;
                cartAmount.setText(updated);
            }
        } else cartAmount.setText("1");
    }*/


    public void deleteItem(ActionEvent e) {
        DelButton db = (DelButton) e.getSource();
        int row = db.getRow();
        handler.getShoppingCart().getItems().remove(row);
        try {
            refresh();
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    public void setPane() throws IOException {
        Properties prop = new Properties();
        InputStreamReader in = new FileReader("currentView.txt");
        prop.load(in);
        AnchorPane e = FXMLLoader.load(getClass().getResource(prop.getProperty("URL")));
        cartPane.getChildren().setAll(e);

    }

    public void setPane2() throws IOException {
        AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/ShoppingCart.fxml/"));
        cartPane.getChildren().setAll(e);
    }

    public void goToCheckout(ActionEvent event) throws IOException {
        viewChanger.changeStageOverride(event, "/fxml/CheckoutView.fxml");
    }

    public void refresh() {
        try {
            setPane();
            setPane2();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public boolean isDouble(String s) {
        if (s.equals("0") || s.equals("0.0")) {
            s = "a";
        }
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInt(String s) {
        if (s.equals(0)) {
            s = "a";
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void activateEnter(ActionEvent e) {
        refresh();
    }

    public boolean cantBuyHalf(int i) {
        return cart.getItems().get(i).getProduct().getUnitSuffix().equals("st");

    }

    public ShoppingItem showItem(int i) {
        return handler.getShoppingCart().getItems().get(i);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        if (handler.getShoppingCart().getItems().size() > 5) {
            scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        }
        for (int i = 0; i < handler.getShoppingCart().getItems().size(); i++) {
            grid.add(new Text(showItem(i).getProduct().getName()), 0, i);
            CartTextField temp = new CartTextField(i);
            if (cantBuyHalf(temp.getRow())) {
                temp.setText("" + (int) showItem(i).getAmount());
            }
            if (!cantBuyHalf(temp.getRow())) {
                temp.setText("" + showItem(i).getAmount());
            }
            temp.setMaxSize(59, 31);
            temp.setOnMouseClicked(this::amountClicked);
            if (!cantBuyHalf(temp.getRow())) {


                temp.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!isDouble(temp.getText())) {
                            if (isDouble(oldValue)) {
                                temp.setText(oldValue);
                            } else temp.setText("1.0");
                        } else if (!temp.getText().isEmpty()) {
                            cart.getItems().get(temp.getRow()).setAmount(Double.parseDouble(newValue));
                        }
                    }
                });
            }
            if (cantBuyHalf(temp.getRow())) {
                temp.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (!isInt(temp.getText())) {
                            if (isDouble(oldValue)) {
                                int old2 = (int) Double.parseDouble(oldValue);
                                temp.setText(old2 + "");


                            } else temp.setText("1.0");
                        } else if (!temp.getText().isEmpty()) {
                            cart.getItems().get(temp.getRow()).setAmount(Integer.parseInt(newValue));
                        }
                    }
                });
            }
            temp.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) {
                        try {
                            refresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!newValue && (temp.getText().isEmpty() || temp.getText().equals("0"))) {
                        temp.setText("1.0");
                    }
                }
            });
            temp.setOnAction(this::activateEnter);
            grid.add(temp, 1, i);
            Text suffix = new Text(showItem(i).getProduct().getUnitSuffix());
            grid.add(suffix, 2, i);
            grid.add(new Text("" + showItem(i).getProduct().getPrice() * showItem(i).getAmount()), 3, i);
            totalPrice.setText(handler.getShoppingCart().getTotal() + " :-");
            delButton = new DelButton("Ta bort", i);
            delButton.setOnAction(this::deleteItem);
            grid.add(delButton, 4, i);
        }
    }
}





