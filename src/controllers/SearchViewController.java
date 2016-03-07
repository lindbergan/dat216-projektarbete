package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
        List<Product> products = handler.getProducts();
        try {
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("search.txt");
            prop.load(in);
            input = prop.getProperty("input");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        int column = 0;
        int row = 0;
        int changeRow = 0;
        int addRow = 0;
        double magicalHeight = 0.0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().toLowerCase().contains(input)) {
                String url = "/products/images/" + products.get(i).getImageName();
                String price = super.getPriceText(products.get(i));
                ImageView img = new ImageView(new Image(url));
                img.setEffect(new DropShadow(8, Color.BEIGE));
                Button newButton = new Button(price, img);
                newButton.setPrefWidth(200);
                newButton.setPrefHeight(240);
                newButton.setPickOnBounds(false);
                newButton.setFocusTraversable(false);
                img.setFitWidth(newButton.getPrefWidth());
                img.setFitHeight(newButton.getPrefHeight() * 0.6);
                newButton.getStyleClass().add("productButton");
                img.getStyleClass().add("productImage");
                newButton.setContentDisplay(ContentDisplay.TOP);
                newButton.setStyle("-fx-font: 15 system");

                BuyButton newBottomButton = new BuyButton("Köp", products.get(i).getProductId());
                newBottomButton.setPrefWidth(75);
                newBottomButton.setPrefHeight(35);
                newBottomButton.toFront();
                newBottomButton.setAlignment(Pos.CENTER);
                newBottomButton.setPickOnBounds(false);
                newBottomButton.setFocusTraversable(false);
                newBottomButton.setOnAction(this::buyItem);
                newBottomButton.getStyleClass().add("buyButton");

                Label txt = new Label(products.get(i).getName());
                txt.setTextFill(exampleText.getTextFill());
                txt.setFont(exampleText.getFont());

                StackPane panelLayout = new StackPane(newButton, newBottomButton, txt);
                panelLayout.setAlignment(newBottomButton, Pos.BOTTOM_CENTER);
                panelLayout.setAlignment(txt, Pos.TOP_CENTER);
                panelLayout.setMargin(newBottomButton, new Insets(0, 0, 5, 0));
                gridRazz.add(panelLayout, column, changeRow);
                column++;
                row++;
                if (row == 4) {
                    changeRow++;
                    row = 0;
                    column = 0;
                    addRow++;
                }
                if (addRow % 6 == 1) {
                    razzPane.setPrefHeight(magicalHeight + 238);
                    magicalHeight = magicalHeight + 238;
                }
            }

        }
    }
}
