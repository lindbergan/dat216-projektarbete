package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SelectedCategoryMenuController implements Initializable {

    @FXML public GridPane gridPane123;
    @FXML public AnchorPane apGridWindow;
    @FXML private Label exampleText;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exampleText.setVisible(false);
        try {
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("products.txt");
            prop.load(in);
            showProducts(prop.getProperty("category"));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }


    public void showProducts(String category) {
        List<Product> productList;
        switch (category) {
            case "Baljväxter": productList = handler.getProducts(ProductCategory.POD);
                break;
            case "Bröd": productList = handler.getProducts(ProductCategory.BREAD);
                break;
            case "Frukt och grönt": {
                productList = handler.getProducts(ProductCategory.FRUIT);
                productList.addAll(handler.getProducts(ProductCategory.BERRY));
                productList.addAll(handler.getProducts(ProductCategory.CITRUS_FRUIT));
                productList.addAll(handler.getProducts(ProductCategory.EXOTIC_FRUIT));
                productList.addAll(handler.getProducts(ProductCategory.VEGETABLE_FRUIT));
                productList.addAll(handler.getProducts(ProductCategory.CABBAGE));
                productList.addAll(handler.getProducts(ProductCategory.MELONS));
            }
                break;
            case "Skafferi": {
                productList = handler.getProducts(ProductCategory.FLOUR_SUGAR_SALT);
            }
            break;
            case "Sötsaker och drycker": {
                productList = handler.getProducts(ProductCategory.COLD_DRINKS);
                productList.addAll(handler.getProducts(ProductCategory.SWEET));
                productList.addAll(handler.getProducts(ProductCategory.HOT_DRINKS));
            }
            break;
            case "Fisk": {
                productList = handler.getProducts(ProductCategory.FISH);
            }
            break;
            case "Kött": {
                productList = handler.getProducts(ProductCategory.MEAT);
            }
            break;
            case "Mejeri": {
                productList = handler.getProducts(ProductCategory.DAIRIES);
            }
            break;
            case "Nötter och frön": {
                productList = handler.getProducts(ProductCategory.NUTS_AND_SEEDS);
            }
            break;
            case "Pasta, potatis och ris": {
                productList = handler.getProducts(ProductCategory.PASTA);
                productList.addAll(handler.getProducts(ProductCategory.POTATO_RICE));
            }
            break;
            case "Rotfrukter": {
                productList = handler.getProducts(ProductCategory.ROOT_VEGETABLE);
            }
            break;
            default: productList = handler.getProducts();
        }

        if (!(productList.isEmpty())) {
            int productListSize = productList.size();
            int rowNr = 0;
            for (int i = 0; i < productListSize - 1; i=i+4) {
                gridPane123.addRow(rowNr);
                rowNr++;
            }
            int rowNrAgain = 0;
            double magicalHeight = 0.0;
            int magicalIdNr = 0;
            int adrianplz = 0;
            for (int i = 0; i < productListSize - 1; i+=4) {
                for (int j = 0; j < 4; j++) {
                    String url = "/controllers/images/"+productList.get(adrianplz).getImageName();
                    Button newButton = new Button();
                    newButton.setPrefWidth(200);
                    newButton.setPrefHeight(240);
                    newButton.setPickOnBounds(false);
                    newButton.setFocusTraversable(false);
                    ImageView img = new ImageView(new Image(url));
                    img.setFitWidth(200);
                    img.setFitHeight(240);
                    newButton.setGraphic(img);

                    Button newBottomButton = new Button("Köp");
                    newBottomButton.setPrefWidth(75);
                    newBottomButton.setPrefHeight(35);
                    newBottomButton.toFront();
                    newBottomButton.setAlignment(Pos.CENTER);
                    newBottomButton.setPickOnBounds(false);
                    newBottomButton.setFocusTraversable(false);

                    Label txt = new Label(productList.get(adrianplz).getName());
                   // txt.setPrefSize(75,75);
                    txt.setTextFill(exampleText.getTextFill());
                    txt.setFont(exampleText.getFont());

                    StackPane panelLayout = new StackPane(newButton, newBottomButton,txt);
                    panelLayout.setAlignment(newBottomButton, Pos.BOTTOM_CENTER);
                    panelLayout.setAlignment(txt,Pos.TOP_CENTER);
                    gridPane123.add(panelLayout, j, rowNrAgain);
                    magicalIdNr++;
                    adrianplz = adrianplz + 1;
                }
                if (rowNrAgain < rowNr) {
                    rowNrAgain++;
                    apGridWindow.setPrefHeight(magicalHeight + 250);
                    magicalHeight = magicalHeight + 250;
                }
            }
        }
        else {
            System.out.println("Productlist is empty. ");
        }
    }

}
