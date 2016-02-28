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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @FXML private MenuButton cartMenuButton;
    @FXML private AnchorPane bp1iMatCategoryAP;
    @FXML private Button helpButton;
    private Stage helpStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i = 0; i<5; i++){
            ShoppingItem item = handler.getShoppingCart().getItems().get(i);
            MenuItem temp = new MenuItem(item.getProduct().getName() + "     " + item.getAmount() + "   "+item.getProduct().getUnitSuffix() + "  " + item.getProduct().getPrice() + " :-");
            cartMenuButton.getItems().add(i, temp);
        }
        cartMenuButton.getItems().add(5,new MenuItem("..."));

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

    //fetches the shopping cart view and replaces current anchor pane with it
    public void goToCart()throws IOException {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/ShoppingCart.fxml/"));
            bp1iMatCategoryAP.getChildren().setAll(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //toggles the help menu
    public void helpMenu() throws IOException{
        Parent helpParent = FXMLLoader.load(getClass().getResource("/fxml/helpMenu.fxml"));
        Scene helpScene = new Scene(helpParent);
        helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.show();
    }
    //closes help menu when pressing the button
    public void closeWindow(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}
