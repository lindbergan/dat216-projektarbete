package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class categoryMenuController extends ProductView implements Initializable {

    @FXML
    public GridPane gridPane;
    @FXML
    public AnchorPane apGridWindow;
    @FXML
    public AnchorPane bp1CategoryAP;
    @FXML
    public AnchorPane categoryMenuAP;
    @FXML private Label hideThis;

    public String[] productCategories = {"Baljväxter", "Bröd", "Frukt och grönt", "Skafferi", "Sötsaker och drycker", "Fisk"
            , "Kött", "Mejeri", "Nötter och frön", "Pasta, potatis och ris", "Rotfrukter"};

    public static AnchorPane categoryMenuAPproperty;
    private IMatDataHandler handler = IMatDataHandler.getInstance();

    public String[] getProductCategories() {
        return productCategories;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryMenuAPproperty = categoryMenuAP;
        hideThis.setVisible(false);
        showProductCategories();
        gridPane.setPadding(new Insets(10, 0, 0, 0));
        gridPane.setVgap(10);
    }

    /***
     * 1.  "Baljväxter"
     * 2.  "Bröd"
     * 3.  "Frukt och grönt (inkl. bär)"
     * 4.  "Skafferi" (inkl. Drycker varma, mjöl socker salt)
     * 5.  "Sötsaker och drycker" (inkl. kalla drycker, sötsaker)
     * 6.  "Fisk"
     * 7.  "Kött"
     * 8.  "Mejeri"
     * 9.  "Nötter och frön"
     * 10. "Pasta potatis och ris"
     * 11. "Rotfrukter"
     */

    public void setPane() {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/SelectedCategoryMenu.fxml/"));
            categoryMenuAPproperty.getChildren().setAll(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showProducts(String category) {
        super.showProducts(category);
    }

    public void showProductCategories() {

        int categoryListSize = productCategories.length;
        int rowNr = 0;
        double magicalHeight = 0.0;

        for (int i = 0; i < categoryListSize; i = i + 4) {
            gridPane.addRow(rowNr);
            rowNr++;
        }

        int magicalNr = 0;
        int rowNrAgain = 0;
        int adrianplz = 0;
        for (int i = 0; i < categoryListSize; i += 4) {
            for (int j = 0; j < 4; j++) {

                Button newButton = new Button();
                newButton.setPrefWidth(200);
                newButton.setPrefHeight(250);
                showProducts(productCategories[magicalNr]);
                String url = "/products/images/" + productList.get(2).getImageName();
                ImageView img = new ImageView(new Image(url));
                img.setFitWidth(newButton.getPrefWidth());
                img.setFitHeight(newButton.getPrefHeight()*0.6);
                img.setEffect(new DropShadow(8, Color.BEIGE));
                newButton.getStyleClass().add("productButton");
                newButton.setGraphic(img);
                Label txt = new Label(productCategories[magicalNr]);
                txt.setFont(hideThis.getFont());
                txt.setTextFill(hideThis.getTextFill());
                newButton.setPickOnBounds(false);
                newButton.setFocusTraversable(false);
                StackPane p = new StackPane(newButton,txt);
                p.setAlignment(txt,Pos.TOP_CENTER);
                newButton.setOnAction(e -> {
                    if (!(txt.getText() == null || txt.getText().equals(""))) {
                        try {
                            Properties prop = new Properties();

                            FileOutputStream out = new FileOutputStream("products.txt");
                            prop.setProperty("category", txt.getText());
                            prop.store(out, null);
                            setPane();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                gridPane.add(p, j, rowNrAgain);
                if (magicalNr < 10) {
                    magicalNr++;
                }
                if (adrianplz < categoryListSize - 1) {
                    adrianplz++;
                } else break;
            }

            if (rowNrAgain < rowNr) {
                rowNrAgain++;
                apGridWindow.setPrefHeight(magicalHeight + 250);
                magicalHeight = magicalHeight + 250;
            }
        }
    }

}
