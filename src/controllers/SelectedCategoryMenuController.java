package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SelectedCategoryMenuController implements Initializable {

    @FXML public GridPane gridPane;
    @FXML public AnchorPane apGridWindow;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProducts();
    }

    public void showProducts() {
        List<Product> productList = handler.getProducts();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(10));
        if (!(productList.isEmpty())) {
            int productListSize = productList.size();
            int rowNr = 0;
            for (int i = 0; i < productListSize - 1; i=i+4) {
                gridPane.addRow(rowNr);
                rowNr++;
            }
            int rowNrAgain = 0;
            double magicalHeight = 483.0;
            int magicalIdNr = 0;
            for (int i = 0; i < productListSize - 1; i+=4) {
                for (int j = 0; j < 4; j++) {

                    Button newButton = new Button("" + magicalIdNr);
                    newButton.setPrefWidth(200);
                    newButton.setPrefHeight(250);
                    newButton.setPickOnBounds(false);
                    newButton.setFocusTraversable(false);

                    Button newBottomButton = new Button("Köp");
                    newBottomButton.setPrefWidth(75);
                    newBottomButton.setPrefHeight(35);
                    newBottomButton.toFront();
                    newBottomButton.setAlignment(Pos.CENTER);
                    newBottomButton.setPickOnBounds(false);
                    newBottomButton.setFocusTraversable(false);

                    StackPane panelLayout = new StackPane(newButton, newBottomButton);
                    panelLayout.setAlignment(newBottomButton, Pos.BOTTOM_CENTER);
                    gridPane.add(panelLayout, j, rowNrAgain);
                    magicalIdNr++;
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
