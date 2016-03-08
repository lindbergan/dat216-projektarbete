package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import properties.ListCellProducts;
import properties.ViewChanger;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SelectedReceiptController implements Initializable {

    @FXML private Button backButton;
    @FXML private ListView listView;
    @FXML private Text totalText;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    public static int variable = 0;

    static Order o;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewChanger vc = new ViewChanger();
        backButton.setOnAction(e -> {
            try {
                vc.changeScene(IMatController.contentProperty, "/fxml/Receipts.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        ObservableList<ShoppingItem> items = FXCollections.observableArrayList();
        items.addAll(o.getItems().stream().collect(Collectors.toList()));

        listView.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ListCellProducts(o, items);
            }
        });
        variable = 0;
        listView.setItems(items);

        double sum = 0;

        for (ShoppingItem shi: o.getItems()) {
            sum = sum + (shi.getProduct().getPrice()*shi.getAmount());
        }
        totalText.setText(String.valueOf(sum));

    }

    public static void setOrder(Order op) {
        o = op;
    }
}
