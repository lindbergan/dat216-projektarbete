package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import properties.ProductListCell;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectedListController implements Initializable {

    @FXML
    private Label listName;
    @FXML private ListView listView;

    ObservableList<Product> products = FXCollections.observableArrayList();

    IMatController handler = new IMatController();
    IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        products.addAll(dataHandler.getProduct(1));
        listName.setText("Namn: " + handler.getTempName());
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ProductListCell();
            }
        });
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void removeProduct(Product p) {
        products.remove(p);
    }

    public void clear() {
        products.clear();
    }
}
