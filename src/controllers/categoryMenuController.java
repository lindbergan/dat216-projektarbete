package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

public class categoryMenuController implements Initializable {

    @FXML public GridPane gridPane;
    @FXML public AnchorPane apGridWindow;
    IMatController handler = new IMatController();
    SelectedCategoryMenuController categoryHandler = new SelectedCategoryMenuController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProductCategories();
    }

    /***
     *
     *
     *  1.  "Baljväxter"
     *  2.  "Bröd"
     *  3.  "Frukt och grönt (inkl. bär)"
     *  4.  "Skafferi" (inkl. Drycker varma, mjöl socker salt)
     *  5.  "Sötsaker och drycker" (inkl. kalla drycker, sötsaker)
     *  6.  "Fisk"
     *  7.  "Kött"
     *  8.  "Mejeri"
     *  9.  "Nötter och frön"
     *  10. "Pasta potatis och ris"
     *  11. "Rotfrukter"
     *
     *
     */

    public void showProductCategories() {
        String[] productCategories = {"Baljväxter", "Bröd", "Frukt och grönt", "Skafferi", "Sötsaker och drycker", "Fisk"
        , "Kött", "Mejeri", "Nötter och frön", "Pasta, potatis och ris", "Rotfrukter"};

        int categoryListSize = productCategories.length;
        int rowNr = 0;
        double magicalHeight = 483.0;

        for (int i = 0; i < categoryListSize; i=i+4) {
            gridPane.addRow(rowNr);
            rowNr++;
        }

        int magicalNr = 0;
        int rowNrAgain = 0;
        for (int i = 0; i < categoryListSize; i+=4) {
            for (int j = 0; j < 4; j++) {

                Button newButton = new Button(productCategories[magicalNr]);
                newButton.setPrefWidth(200);
                newButton.setPrefHeight(250);
                newButton.setPickOnBounds(false);
                newButton.setFocusTraversable(false);
                newButton.setOnAction(e -> {
                    /*if (!(newButton.getText() == null || newButton.getText().equals(""))) {
                        goToSelectedCategoryMenu(newButton.getText());
                    }*/
                });

                gridPane.add(newButton, j, rowNrAgain);
                if (magicalNr < 10) {magicalNr++;}
            }

            if (rowNrAgain < rowNr) {
                rowNrAgain++;
                apGridWindow.setPrefHeight(magicalHeight + 250);
                magicalHeight = magicalHeight + 250;
            }
        }
    }

}
