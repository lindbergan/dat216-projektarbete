package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import properties.BuyButton;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SearchViewController extends ProductView implements Initializable {
    IMatDataHandler handler = IMatDataHandler.getInstance();
    @FXML
    GridPane gridRazz;
    @FXML
    AnchorPane razzPane;
    private String input;
    @FXML
    private Label exampleText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exampleText.setVisible(false);
        try {
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("search.txt");
            prop.load(in);
            input = prop.getProperty("input");
            DataHolder.iMat.deselectCategory();
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        int adrianplz = 0;

        List<Product> products = handler.findProducts(input);
        int productListSize = products.size();
        int rowNr = 0;
        for (int i = 0; i < productListSize - 1; i = i + 4) {
            gridRazz.addRow(rowNr);
            rowNr++;
        }
        int rowNrAgain = 0;
        double magicalHeight = 0.0;

        for (int i = 0; i < products.size(); i += 4) {
            for (int j = 0; j < 4; j++) {
                String url = "/products/images/" + products.get(adrianplz).getImageName();
                String price = super.getPriceText(products.get(adrianplz));
                ImageView img = new ImageView(new Image(url));
                img.setEffect(new DropShadow(8, Color.BEIGE));
                Button newButton = new Button(price, img);
                newButton.setPrefWidth(200);
                newButton.setPrefHeight(230);
                newButton.setPickOnBounds(false);
                newButton.setFocusTraversable(false);

                img.setFitWidth(newButton.getPrefWidth());
                img.setFitHeight(newButton.getPrefHeight() * 0.51);
                newButton.getStyleClass().add("productButton");
                img.getStyleClass().add("productImage");
                newButton.setContentDisplay(ContentDisplay.TOP);
                newButton.setStyle("-fx-font: 15 system");

                BuyButton newBottomButton = new BuyButton("Köp", products.get(adrianplz).getProductId());
                newBottomButton.setPrefWidth(75);
                newBottomButton.setPrefHeight(25);
                newBottomButton.toFront();
                newBottomButton.setAlignment(Pos.CENTER);
                newBottomButton.setPickOnBounds(false);
                newBottomButton.setFocusTraversable(false);
                newBottomButton.setOnAction(this::buyItem);
                newBottomButton.getStyleClass().add("buyButton");
                newBottomButton.setCursor(Cursor.HAND);

                Label txt = new Label(products.get(adrianplz).getName());
                txt.setTextFill(exampleText.getTextFill());
                txt.setFont(exampleText.getFont());


                StackPane panelLayout = new StackPane(newButton, newBottomButton, txt);
                panelLayout.setAlignment(newBottomButton, Pos.BOTTOM_CENTER);
                panelLayout.setAlignment(txt, Pos.TOP_CENTER);
                panelLayout.setMargin(newBottomButton, new Insets(0, 0, 5, 0));
                gridRazz.add(panelLayout, j, rowNrAgain);
                if (getProductInCart(products.get(adrianplz)) != null) {
                    newBottomButton.fire();
                }
                if (adrianplz < productListSize - 1) {
                    adrianplz = adrianplz + 1;
                } else break;
            }
            if (rowNrAgain < rowNr) {
                rowNrAgain++;
                razzPane.setPrefHeight(magicalHeight + 250);
                magicalHeight = magicalHeight + 250;
            }
            gridRazz.setPadding(new Insets(20, 0, 20, 0));
            gridRazz.setVgap(10);
        }

    }
}

