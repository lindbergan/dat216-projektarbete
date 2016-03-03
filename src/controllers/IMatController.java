package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties.CategoryListCell;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    @FXML private Button searchButton;
    @FXML private TextField searchField;
    @FXML
    public AnchorPane content;
    @FXML
    public ImageView imageView1;
    @FXML
    private MenuButton cartMenuButton;
    @FXML
    private Button helpButton;
    @FXML
    private ToggleButton toggle1;
    @FXML
    private ToggleButton toggle2;
    @FXML
    private MenuButton receiptMenu;
    @FXML
    private MenuButton favoritesMenu;
    @FXML
    private MenuButton listMenu;
    @FXML
    private AnchorPane bp1iMatCategoryAP;
    @FXML
    private MenuItem totalMenu;
    @FXML
    private ListView<String> listView;
    public static ListView<String> listProperty;

    IMatDataHandler handler = IMatDataHandler.getInstance();
    categoryMenuController categoryHandler = new categoryMenuController();
    private boolean isShopView;
    private MenuItem temp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImage();
        start();
        initToggleButtons();
        ifNoReciepts();
        ifNoFavorites();
        ifNoLists();
        initButtons();
        initListView();
        listProperty = listView;
    }

    public ListView<String> getListProperty() {
        return listProperty;
    }

    public void initListView() {
        ObservableList<String> categories = FXCollections.observableArrayList(categoryHandler.getProductCategories());
        listView.setItems(categories);
        listView.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                Properties prop = new Properties();
                FileOutputStream out = new FileOutputStream("products.txt");
                prop.setProperty("category", listView.getSelectionModel().getSelectedItem());
                prop.store(out, null);
                goToCategoryMenu();
                categoryHandler.setPane();
            }
            catch (IOException ee) {
                ee.printStackTrace();
            }
        });
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new CategoryListCell();
            }
        });
        double height = categoryHandler.getProductCategories().length * 40;
        listView.setPrefHeight(height);
    }

    public void ifNoFavorites() {
        if (favoritesMenu.getItems().isEmpty()) {
            MenuItem newMenuItem = new MenuItem("Inga favoriter.");
            newMenuItem.setDisable(true);
            newMenuItem.styleProperty().set("-fx-pref-width:138px;");
            newMenuItem.styleProperty().set("-fx-pref-height:29px;");
            favoritesMenu.getItems().add(newMenuItem);
        }
    }

    public void ifNoLists() {
        if (listMenu.getItems().isEmpty()) {
            MenuItem newMenuItem = new MenuItem("Skapa ny lista.");
            newMenuItem.styleProperty().set("-fx-pref-width:159px;");
            newMenuItem.styleProperty().set("-fx-pref-height:29px;");
            listMenu.getItems().add(newMenuItem);
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
            newMenuItem.styleProperty().set("-fx-pref-width:175px;");
            newMenuItem.styleProperty().set("-fx-pref-height:29px;");
            receiptMenu.getItems().add(newMenuItem);
        }
    }

    public void setImage() {
        Image image = new Image("/images/IMat-logga.png/");
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
    public void goToCart() throws IOException {
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
            helpStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cartMenuOnAction() {
        cartMenuButton.getItems().remove(0, cartMenuButton.getItems().size() - 3);
        int limit = 5;
        if (handler.getShoppingCart().getItems().size() < limit) {
            limit = handler.getShoppingCart().getItems().size();
        }
        if (handler.getShoppingCart().getItems().size() != 0) {
            for (int i = 0; i < limit; i++) {
                ShoppingItem item = handler.getShoppingCart().getItems().get(i);
                if(!cantBuyHalf(item.getProduct().getProductId())) {
                    temp = new MenuItem(item.getProduct().getName() + "     " + item.getAmount() + "   " + item.getProduct().getUnitSuffix() + "  " + item.getProduct().getPrice() + " :-");
                }
                if(cantBuyHalf(item.getProduct().getProductId())){
                    temp = new MenuItem(item.getProduct().getName() + "     " + (int) item.getAmount() + "   " + item.getProduct().getUnitSuffix() + "  " + item.getProduct().getPrice() + " :-");
                }
                cartMenuButton.getItems().add(i, temp);
            }
        }
        if (handler.getShoppingCart().getItems().size() > 5) {
            cartMenuButton.getItems().add(5, new MenuItem("..."));
            cartMenuButton.getItems().add(6, new SeparatorMenuItem());
        } else {
            if (handler.getShoppingCart().getItems().size() != 0) {
                cartMenuButton.getItems().add(handler.getShoppingCart().getItems().size(), new SeparatorMenuItem());
            }
        }

        totalMenu.setText("Totalt:" + "  " + handler.getShoppingCart().getTotal() + " :-");
    }
    public boolean cantBuyHalf(int i){
        return handler.getProduct(i).getUnitSuffix().equals("st");

    }

    public void currentView() {
        isShopView = toggle1.isSelected();
        String url;
        if (isShopView) {
            url = "/fxml/shopView.fxml/";
        } else url = "/fxml/categoryMenu.fxml/";
        try {
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("currentView.txt");
            prop.load(in);

            FileOutputStream out = new FileOutputStream("currentView.txt");
            prop.setProperty("URL", url);
            prop.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void search(){
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/searchView.fxml/"));
            content.getChildren().setAll(e);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        String input = searchField.getText().toLowerCase();
        Properties prop = new Properties();
        try {
            FileOutputStream out = new FileOutputStream("search.txt");
            prop.setProperty("input", input);
            prop.store(out, null);
        }
        catch(Exception e){
            e.getStackTrace();
        }

    }


}