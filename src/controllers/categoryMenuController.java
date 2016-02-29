package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class categoryMenuController implements Initializable {

    @FXML public GridPane gridPane;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showProducts() {
        List<Product> productList = handler.getProducts();
        if (!(productList.isEmpty())) {
            int productListSize = productList.size();
            int rowNr = 0;
            for (int i = 0; i < productListSize - 1; i=i+4) {
                gridPane.addRow(rowNr);
                rowNr++;
            }
            rowNr = 0;
        }
        else {
            System.out.println("Productlist is empty. ");
        }

    }

    public void showProducts(ProductCategory productCategory) {

    }

}
