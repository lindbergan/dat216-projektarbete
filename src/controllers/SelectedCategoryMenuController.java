package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import se.chalmers.ait.dat215.project.ShoppingCart;

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
                    List<Product> favorites = handler.favorites();
                    Product p = handler.getProduct(productList.get(adrianplz).getProductId());
                    String url = "/products/images/" + productList.get(adrianplz).getImageName();
                    String price = super.getPriceText(productList.get(adrianplz));
                    ImageView img = new ImageView(new Image(url));
                    img.setEffect(new DropShadow(8, Color.BEIGE));
                    Button newButton = new Button(price, img);
                    newButton.setPrefWidth(200);
                    newButton.setPrefHeight(240);
                    newButton.setPickOnBounds(false);
                    newButton.setFocusTraversable(false);

                    img.setFitWidth(newButton.getPrefWidth());
                    img.setFitHeight(newButton.getPrefHeight() * 0.535);
                    newButton.getStyleClass().add("productButton");
                    img.getStyleClass().add("productImage");
                    newButton.setContentDisplay(ContentDisplay.TOP);
                    newButton.setStyle("-fx-font: 15 system");

                    BuyButton newBottomButton = new BuyButton("Köp", productList.get(adrianplz).getProductId());
                    newBottomButton.setPrefWidth(75);
                    newBottomButton.setPrefHeight(25);
                    newBottomButton.toFront();
                    newBottomButton.setAlignment(Pos.CENTER);
                    newBottomButton.setPickOnBounds(false);
                    newBottomButton.setFocusTraversable(false);
                    newBottomButton.setOnAction(this::buyItem);
                    newBottomButton.getStyleClass().add("buyButton");
                    newBottomButton.setCursor(Cursor.HAND);

                    Image image = new Image("products/unselectedFavorite.png");
                    Image selImage = new Image("products/selectedFavorite.png");
                    ImageView im = new ImageView(image);
                    im.setFitHeight(25);
                    im.setFitWidth(25);
                    ImageView selIm = new ImageView(selImage);
                    selIm.setFitHeight(25);
                    selIm.setFitWidth(25);

                    Button favoriteB = new Button();
                    favoriteB.setStyle("-fx-background-color:Transparent;");
                    favoriteB.setCursor(Cursor.HAND);
                    favoriteB.setPadding(new Insets(10, 0, 0, 20));
                    favoriteB.setMaxWidth(im.getFitWidth());
                    favoriteB.setMaxHeight(im.getFitHeight());

                    if (!(p == null)) {
                        if (favorites.contains(p)) favoriteB.setGraphic(selIm);
                        else favoriteB.setGraphic(im);
                    }
                    favoriteB.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (handler.isFavorite(p)) {
                                favoriteB.setGraphic(im);
                                if (!(p == null)) {
                                    handler.favorites().remove(p);
                                    DataHolder.iMat.updateFavorites();
                                }
                            } else {
                                if (!(p == null)) handler.favorites().add(p);
                                favoriteB.setGraphic(selIm);
                                DataHolder.iMat.updateFavorites();
                            }
                        }
                    });

                    Label txt = new Label(productList.get(adrianplz).getName());
                    txt.setTextFill(exampleText.getTextFill());
                    txt.setFont(exampleText.getFont());

                    StackPane panelLayout = new StackPane(newButton, newBottomButton, txt, favoriteB);
                    panelLayout.setAlignment(newBottomButton, Pos.BOTTOM_CENTER);
                    panelLayout.setAlignment(txt, Pos.TOP_CENTER);
                    panelLayout.setAlignment(favoriteB, Pos.TOP_LEFT);
                    panelLayout.setMargin(newBottomButton, new Insets(0, 0, 5, 0));
                    gridPane123.add(panelLayout, j, rowNrAgain);
                    if (getProductInCart(productList.get(adrianplz)) != null) {
                        newBottomButton.fire();
                    }
                    if (adrianplz < productListSize - 1) {
                        adrianplz = adrianplz + 1;
                    } else break;
                }
                if (rowNrAgain < rowNr) {
                    rowNrAgain++;
                    apGridWindow.setPrefHeight(magicalHeight + 250);
                    magicalHeight = magicalHeight + 250;
                }
                gridPane123.setPadding(new Insets(20, 0, 20, 0));
            }
        } else {
            System.out.println("Productlist is empty. ");
        }

    }
}
