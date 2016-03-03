package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import properties.BuyButton;
import se.chalmers.ait.dat215.project.*;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SelectedCategoryMenuController extends ProductView implements Initializable {

    @FXML
    public GridPane gridPane123;
    @FXML
    public AnchorPane apGridWindow;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    ShoppingCart cart = handler.getShoppingCart();
    @FXML
    private Label exampleText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exampleText.setVisible(false);
        gridPane123.setVgap(10);
        gridPane123.setPadding(new Insets(10, 0, 0, 0));
        try {
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("products.txt");
            prop.load(in);
            showProducts(prop.getProperty("category"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void buyItem(ActionEvent e) {
        BuyButton bb = (BuyButton) e.getSource();
        StackPane p = (StackPane) bb.getParent();

        Button posButton = new Button("+");
        Button negButton = new Button("-");
        TextField tf = new TextField("1");
        tf.setPrefWidth(50);
        tf.setAlignment(Pos.CENTER);

        p.getChildren().remove(bb);
        incItem(bb.getProductId());

        HBox hbox = new HBox(10, negButton, tf, posButton);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        p.getChildren().add(hbox);
        p.setAlignment(hbox, Pos.BOTTOM_CENTER);

        posButton.setOnAction(ee -> {
            incItem(bb.getProductId());
            int amount = Integer.parseInt(tf.getText());
            amount++;
            tf.setText(String.valueOf(amount));
        });

        negButton.setOnAction(ee -> {
            int amount = Integer.parseInt(tf.getText());
            if (amount < 2) {
                hbox.getChildren().removeAll();
                p.getChildren().removeAll();
                hbox.setVisible(false);
                p.getChildren().add(bb);
                decItem(bb.getProductId());
            } else {
                amount--;
                tf.setText(String.valueOf(amount));
                decItem(bb.getProductId());
            }
        });


    }

    public void incItem(int idd) {
        int id = idd;
        int razzan = 0;
        if (cart.getItems().size() == 0) {
            cart.addItem((new ShoppingItem(handler.getProduct(id))));
        } else
            for (int i = 0; i < cart.getItems().size(); i++) {
                if (cart.getItems().get(i).getProduct().getProductId() == id) {
                    cart.getItems().get(i).setAmount(cart.getItems().get(i).getAmount() + 1);
                } else {
                    razzan = razzan + 1;
                }

            }
        if (razzan == cart.getItems().size()) {
            cart.addItem((new ShoppingItem(handler.getProduct(id))));
        }
    }

    public void decItem(int idd) {
        int id = idd;
        if (cart.getItems().size() == 0) {
            cart.addItem((new ShoppingItem(handler.getProduct(id))));
        } else
            for (int i = 0; i < cart.getItems().size(); i++) {
                if (cart.getItems().get(i).getProduct().getProductId() == id) {
                    if (cart.getItems().get(i).getAmount() > 1) {
                        cart.getItems().get(i).setAmount(cart.getItems().get(i).getAmount() - 1);
                    } else cart.getItems().remove(i);
                }

            }
    }

    public void showProducts(String category) {
        super.showProducts(category);

        if (!(productList.isEmpty())) {
            int productListSize = productList.size();
            int rowNr = 0;
            for (int i = 0; i < productListSize - 1; i = i + 4) {
                gridPane123.addRow(rowNr);
                rowNr++;
            }
            int rowNrAgain = 0;
            double magicalHeight = 0.0;
            int adrianplz = 0;
            for (int i = 0; i < productListSize - 1; i += 4) {
                for (int j = 0; j < 4; j++) {
                    String url = "/products/images/" + productList.get(adrianplz).getImageName();
                    Button newButton = new Button();
                    newButton.setPrefWidth(200);
                    newButton.setPrefHeight(240);
                    newButton.setPickOnBounds(false);
                    newButton.setFocusTraversable(false);
                    ImageView img = new ImageView(new Image(url));
                    img.setFitWidth(newButton.getPrefWidth());
                    img.setFitHeight(newButton.getPrefHeight()*0.6);
                    img.setEffect(new DropShadow(8, Color.BEIGE));
                    newButton.getStyleClass().add("productButton");
                    img.getStyleClass().add("productImage");
                    newButton.setGraphic(img);


                    BuyButton newBottomButton = new BuyButton("Köp", productList.get(adrianplz).getProductId());
                    newBottomButton.setPrefWidth(75);
                    newBottomButton.setPrefHeight(35);
                    newBottomButton.toFront();
                    newBottomButton.setAlignment(Pos.CENTER);
                    newBottomButton.setPickOnBounds(false);
                    newBottomButton.setFocusTraversable(false);
                    newBottomButton.setOnAction(this::buyItem);
                    newBottomButton.getStyleClass().add("buyButton");

                    Label txt = new Label(productList.get(adrianplz).getName());
                    txt.setTextFill(exampleText.getTextFill());
                    txt.setFont(exampleText.getFont());

                    StackPane panelLayout = new StackPane(newButton, newBottomButton, txt);
                    panelLayout.setAlignment(newBottomButton, Pos.BOTTOM_CENTER);
                    panelLayout.setAlignment(txt, Pos.TOP_CENTER);
                    panelLayout.setMargin(newBottomButton, new Insets(0, 0, 5, 0));
                    gridPane123.add(panelLayout, j, rowNrAgain);
                    if (adrianplz < productListSize - 1) {
                        adrianplz = adrianplz + 1;
                    } else break;
                }
                if (rowNrAgain < rowNr) {
                    rowNrAgain++;
                    apGridWindow.setPrefHeight(magicalHeight + 250);
                    magicalHeight = magicalHeight + 250;
                }
            }
        } else {
            System.out.println("Productlist is empty. ");
        }
    }

}
