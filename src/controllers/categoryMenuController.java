package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class categoryMenuController implements Initializable {

    @FXML private AnchorPane bp1CategoryAP;
    @FXML private GridPane bp1CategoryGP;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*int rowNr = 0;
        bp1CategoryGP.setGridLinesVisible(true);
        for (int i = 0; i < IMatDataHandler.getInstance().getProducts().size() - 1; i=+4) {
            bp1CategoryGP.addRow(rowNr);
            rowNr++;

        }*/
    }
}
