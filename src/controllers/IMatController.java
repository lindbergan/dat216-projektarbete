package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {

    @FXML private MenuButton cartMenuButton;
    @FXML private AnchorPane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    //gets the view where the dropdown menu for the cart is, hides it and then opens the shopping cart view
    public void goToCart()throws IOException {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/ShoppingCart.fxml/"));
            content.getChildren().setAll(e);
            e.setLayoutX(-34);
            e.setLayoutY(-34);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
