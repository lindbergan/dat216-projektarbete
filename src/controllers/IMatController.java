package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {

    @FXML private MenuButton cartMenuButton;
    @FXML private Button helpButton;
    @FXML private AnchorPane content;
    @FXML public ImageView imageView1;
    @FXML private ToggleButton toggle1;
    @FXML private ToggleButton toggle2;
    @FXML private MenuButton receiptMenu;
    @FXML private MenuButton favoritesMenu;
    private Stage helpStage;
    @FXML private MenuItem totalMenu;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImage();
        start();
        initToggleButtons();
        ifNoReciepts();
        ifNoFavorites();
        initButtons();
    }

    public void ifNoFavorites() {
        if (favoritesMenu.getItems().isEmpty()) {
            MenuItem newMenuItem = new MenuItem("Inga favoriter.");
            newMenuItem.setDisable(true);
            favoritesMenu.getItems().add(newMenuItem);
        }
    }

    public void initButtons() {
        helpButton.setOnAction(e -> {
            helpMenu();
        });
    }

    public void ifNoReciepts() {
        if (receiptMenu.getItems().isEmpty()) {
            MenuItem newMenuItem = new MenuItem("Inga kvitton.");
            newMenuItem.setDisable(true);
            receiptMenu.getItems().add(newMenuItem);
        }
    }

    public void setImage() {
        Image image = new Image("/images/redness.png/");
        imageView1.setImage(image);
    }

    public void initToggleButtons() {
        toggle1.setOnAction(e -> {
            toggle1.setSelected(true);
            toggle2.setSelected(false);
            start();
        });
        toggle2.setOnAction(e -> {
            toggle2.setSelected(true);
            toggle1.setSelected(false);
            goToCategoryMenu();
        });
    }

    public void goToCategoryMenu() {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/categoryMenu.fxml/"));
            content.getChildren().setAll(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/shopView.fxml/"));
            content.getChildren().setAll(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //fetches the shopping cart view and replaces current anchor pane with it
    public void goToCart()throws IOException {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/ShoppingCart.fxml/"));
            content.getChildren().setAll(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //toggles the help menu
    public void helpMenu() {
        try {
            Parent helpParent = FXMLLoader.load(getClass().getResource("/fxml/helpMenu.fxml"));
            Scene helpScene = new Scene(helpParent);
            Stage helpStage = new Stage();
            helpStage.setScene(helpScene);
            helpStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void cartMenuOnAction(){
        cartMenuButton.getItems().remove(0,cartMenuButton.getItems().size()-3);
        int limit = 5;
        if(handler.getShoppingCart().getItems().size()<limit){
            limit = handler.getShoppingCart().getItems().size();
        }
        if(handler.getShoppingCart().getItems().size() != 0) {
            for (int i = 0; i < limit; i++) {
                ShoppingItem item = handler.getShoppingCart().getItems().get(i);
                MenuItem temp = new MenuItem(item.getProduct().getName() + "     " + item.getAmount() + "   " + item.getProduct().getUnitSuffix() + "  " + item.getProduct().getPrice() + " :-");
                cartMenuButton.getItems().add(i, temp);
            }
        }
        if(handler.getShoppingCart().getItems().size() > 5) {
            cartMenuButton.getItems().add(5, new MenuItem("..."));
        }
        else{
            cartMenuButton.getItems().add(handler.getShoppingCart().getItems().size(),new MenuItem(""));
        }

        totalMenu.setText("Totalt:" + "  " + handler.getShoppingCart().getTotal() + " :-");
    }
    //closes help menu when pressing the button
    public void closeWindow(ActionEvent event){
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }


}
