package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {

    @FXML private static AnchorPane bp1iMatCategoryAP;
    @FXML private TableView<String> bp1Tableview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**ObservableList<String> categories = FXCollections.observableArrayList();
        for (ProductCategory category : ProductCategory.values()) {
            categories.add(translateCategories(category));
        }
        bp1Tableview.setItems(categories);**/
    }

    public String translateCategories(ProductCategory p) {
        if (p.toString().equalsIgnoreCase("POD")) return "Baljväxter";
        if (p.toString().equalsIgnoreCase("BREAD")) return "Bröd";
        if (p.toString().equalsIgnoreCase("BERRY")) return "Bär";
        if (p.toString().equalsIgnoreCase("CITRUS_FRUIT")) return "Citrusfrukter";
        if (p.toString().equalsIgnoreCase("HOT_DRINKS")) return "Varma drycker";
        if (p.toString().equalsIgnoreCase("COLD_DRINKS")) return "Kalla drycker";
        if (p.toString().equalsIgnoreCase("EXOTIC_FRUIT")) return "Exotiska frukter";
        if (p.toString().equalsIgnoreCase("FISH")) return "Fisk";
        if (p.toString().equalsIgnoreCase("VEGETABLE_FRUIT")) return "Frukt och grönsaker";
        if (p.toString().equalsIgnoreCase("CABBAGE")) return "Sallad";
        if (p.toString().equalsIgnoreCase("MEAT")) return "Kött";
        if (p.toString().equalsIgnoreCase("DAIRIES")) return "Mejeriprodukter";
        if (p.toString().equalsIgnoreCase("MELONS")) return "Meloner";
        if (p.toString().equalsIgnoreCase("FLOUR_SUGAR_SALT")) return "Skafferi";
        if (p.toString().equalsIgnoreCase("NUTS_AND_SEEDS")) return "Nötter och frön";
        if (p.toString().equalsIgnoreCase("PASTA")) return "Pasta";
        if (p.toString().equalsIgnoreCase("POTATO_RICE")) return "Ris och potatis";
        if (p.toString().equalsIgnoreCase("ROOT_VEGETABLE")) return "Rotgrönsaker";
        if (p.toString().equalsIgnoreCase("FRUIT")) return "Frukt";
        if (p.toString().equalsIgnoreCase("SWEET")) return "Sötsaker";
        if (p.toString().equalsIgnoreCase("HERB")) return "Örter och kryddor";
        return null;
    }
}
