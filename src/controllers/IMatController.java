package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties.CategoryListCell;
import properties.ShoppingCartMenuItem;
import properties.ViewChanger;
import se.chalmers.ait.dat215.project.*;

import javax.management.StringValueExp;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    public static ListView<String> listProperty;
    public static AnchorPane contentProperty;
    public static MenuButton listMenuProperty;

    @FXML
    public AnchorPane content;
    @FXML private MenuItem shoppingCartItem;
    @FXML
    public ImageView imageView1;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    CategoryMenuController categoryHandler = new CategoryMenuController();
    @FXML
    AnchorPane headerPane;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
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
    private MenuItem totalMenu;
    @FXML
    private ListView<String> listView;
    @FXML Button ohKnapp;

    private Properties prop = new Properties();
    private boolean isShopView;
    private MenuItem temp;
    Timer timer = new Timer(1500,new TimerListener());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
        init();
        ohKnapp.setLayoutX(ohKnapp.getLayoutX() + 170);
        ohKnapp.setLayoutY(ohKnapp.getLayoutY() + 43);
        ohKnapp.setVisible(false);
        ShoppingCart cart = handler.getShoppingCart();
        cart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged() {
                cartMenuButton.setVisible(false);
                ohKnapp.setVisible(true);
                ohKnapp.setDefaultButton(true);
                timer.start();


            }
        });

    }

    public void init() {
        initToggleButtons();
        initReceipts();
        initButtons();
        initListView();
        initSearch();
        initSettings();
        initProperties();
    }

    public void weHateTraversable() {
        searchButton.setFocusTraversable(false);
        listView.setFocusTraversable(false);
        helpButton.setFocusTraversable(false);
    }

    public void setIds() {
        headerPane.setId("headerPane");
        content.setId("content");
        searchButton.setId("searchButton");
        listView.setId("listView");
    }

    public void setCursors() {
        helpButton.setCursor(Cursor.HAND);
        searchButton.setCursor(Cursor.HAND);
        listView.setCursor(Cursor.HAND);
    }

    public void initSettings() {
        setImage();
        weHateTraversable();
        setIds();
    }

    public void initProperties() {
        DataHolder.iMat = this;
        contentProperty = content;
        listMenuProperty = listMenu;
        listProperty = listView;
        setCursors();
        ifNoItems();
        if (isFirstTime()) {
            helpMenu();
        }

    }

    public void ifNoItems() {
        ifNoFavorites();
        ifNoLists();
    }

    public void initSearch() {
        try {
            File file = new File("search.txt");
            if (!(file.exists())) file.createNewFile();
            FileOutputStream clear = new FileOutputStream("search.txt");
            prop.setProperty("input", "");
            prop.store(clear, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    String input = searchField.getText().toLowerCase();
                    FileOutputStream out = new FileOutputStream("search.txt");
                    prop.setProperty("input", input);
                    prop.store(out, null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public ListView<String> getListProperty() {
        return listProperty;
    }

    public void initListView() {

        ObservableList<String> categories = FXCollections.observableArrayList(categoryHandler.getProductCategories());
        listView.setItems(categories);
        listView.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                if (!(listView.getSelectionModel().getSelectedItem() == null)) {
                    File file = new File("products.txt");
                    if (!(file.exists())) file.createNewFile();
                    Properties prop = new Properties();
                    FileOutputStream out = new FileOutputStream("products.txt");
                    prop.setProperty("category", listView.getSelectionModel().getSelectedItem());
                    prop.store(out, null);
                    goToCategoryMenu();
                    categoryHandler.setPane();
                }
            } catch (IOException ee) {
                ee.printStackTrace();
            }
        });

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new CategoryListCell();
            }
        });
    }

    public void ifNoFavorites() {
        if (favoritesMenu.getItems().isEmpty()) {
            MenuItem newMenuItem = new MenuItem("Inga favoriter.");
            newMenuItem.setDisable(true);
            favoritesMenu.getItems().add(newMenuItem);
        }
    }

    public void ifNoLists() {
        if (listMenu.getItems().isEmpty()) {
            MenuItem newMenuItem = new MenuItem("Inga listor.");
            newMenuItem.setDisable(true);
            listMenu.getItems().add(newMenuItem);
        }
    }

    public void initButtons() {
        helpButton.getStyleClass().add("helpButton");
        helpButton.setOnAction(e -> {
            helpMenu();
        });
    }

    public boolean isFirstTime() {
        File file = new File("help.txt");
        try {
            if (!(file.exists())) file.createNewFile();
            BufferedReader read = new BufferedReader(new FileReader("help.txt"));
            if (read.readLine() == null) {
                Properties prop = new Properties();
                FileOutputStream out = new FileOutputStream("help.txt");
                prop.setProperty("bool", "true");
                prop.store(out, null);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void initReceipts() {
        if (handler.getOrders().size() != 0) {
            ViewChanger vc = new ViewChanger();
            for (Order o : handler.getOrders()) {
                MenuItem mi = new MenuItem(o.getDate().toString());
                mi.setOnAction(e -> {
                    try {
                        vc.changeScene(content, "/fxml/Receipts.fxml/");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
                receiptMenu.getItems().add(mi);
            }
            MenuItem item = new MenuItem("Hantera alla kvitton");
            item.setOnAction(e -> {
                try {
                    vc.changeScene(content, "/fxml/Receipts.fxml");
                } catch (IOException eee) {
                    eee.printStackTrace();
                }
            });
            receiptMenu.getItems().addAll(new SeparatorMenuItem(), item);
        } else {
            MenuItem menuItem = new MenuItem("Inga kvitton");
            menuItem.setDisable(true);
            receiptMenu.getItems().add(menuItem);
        }
    }

    public void setImage() {
        Image image = new Image("/images/IMat-logga.png/");
        imageView1.setImage(image);
    }

    public void initToggleButtons() {
        toggle1.setSelected(true);
        toggle1.setId("selected");
        toggle1.setOnAction(e -> {
            toggle1.setId("selected");
            toggle2.setId("notSelected");
            toggle1.setSelected(true);
            toggle2.setSelected(false);
            this.deselectCategory();
            start();
        });
        toggle2.setOnAction(e -> {
            toggle1.setId("notSelected");
            toggle2.setId("selected");
            toggle2.setSelected(true);
            toggle1.setSelected(false);
            this.deselectCategory();
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
            this.deselectCategory();
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
            helpStage.setResizable(false);
            helpStage.setScene(helpScene);
            helpStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String calculateSpaces(String name) {
        int length = name.length();
        String returnThisString = "";
        for (int i = 0; i < 30 - length; i++) {
            returnThisString = returnThisString + " ";
        }
        return returnThisString;
    }

    public String calculateSpacesForInt(String name) {
        int length = name.length();
        String returnThisString = "";
        for (int i = 0; i < 10 - length; i++) {
            returnThisString = returnThisString + " ";
        }
        return returnThisString;
    }

    public void cartMenuOnAction() {
        cartMenuButton.getStyleClass().add("cartMenuButton");
        cartMenuButton.getItems().remove(0, cartMenuButton.getItems().size() - 3);
        int limit = 5;
        if (handler.getShoppingCart().getItems().size() < limit) {
            limit = handler.getShoppingCart().getItems().size();
        }
        if (handler.getShoppingCart().getItems().size() != 0) {
            for (int i = 0; i < limit; i++) {
                ShoppingItem item = handler.getShoppingCart().getItems().get(i);
                if (!cantBuyHalf(item.getProduct().getProductId())) {
                    temp = new MenuItem(item.getProduct().getName() + calculateSpaces(item.getProduct().getName()) + item.getAmount() + calculateSpacesForInt(String.valueOf(item.getAmount())) + item.getProduct().getUnitSuffix() + calculateSpacesForInt(item.getProduct().getUnitSuffix()) + item.getProduct().getPrice() + " :-");
                }
                if (cantBuyHalf(item.getProduct().getProductId())) {
                    temp = new MenuItem(item.getProduct().getName() + calculateSpaces(item.getProduct().getName()) + (int) item.getAmount() + calculateSpacesForInt(String.valueOf(item.getAmount())) + item.getProduct().getUnitSuffix() + calculateSpacesForInt(item.getProduct().getUnitSuffix()) + item.getProduct().getPrice() + " :-");
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
            shoppingCartItem.getStyleClass().add("shoppingCartItem");
        }

        totalMenu.setText("Totalt:" + "  " + handler.getShoppingCart().getTotal() + " :-");
    }

    public boolean cantBuyHalf(int i) {
        return handler.getProduct(i).getUnitSuffix().equals("st") || handler.getProduct(i).getUnitSuffix().equals("förp");

    }

    public void currentView() {
        isShopView = toggle1.isSelected();
        String url;
        if (isShopView) {
            url = "/fxml/shopView.fxml/";
        } else url = "/fxml/categoryMenu.fxml/";
        try {
            File file = new File("currentView.txt");
            if (!(file.exists())) file.createNewFile();
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("currentView.txt");
            prop.load(in);

            FileOutputStream out = new FileOutputStream("currentView.txt");
            prop.setProperty("URL", url);
            prop.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search() {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/searchView.fxml/"));
            content.getChildren().setAll(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void selectCategory(int index) {
        listView.getSelectionModel().select(index);
    }

    public void deselectCategory() {
        listView.getSelectionModel().clearSelection();
    }
    class TimerListener implements ActionListener{

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            cartMenuButton.setVisible(true);
            ohKnapp.setVisible(false);
            timer.stop();
        }
    }
}