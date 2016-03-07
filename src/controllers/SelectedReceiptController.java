package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SelectedReceiptController implements Initializable {

    @FXML private Button backButton;
    @FXML private Button manageButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    @FXML private ListView listView;
    @FXML private Text totalText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> obList = FXCollections.observableArrayList();
        double totalPrice = 0;
        try {
            List<String> arr = Files.readAllLines(Paths.get("receipts.txt"));
            String[] list = arr.get(0).split(";");

            for (int i = 0; i < list.length - 1; i++) {
                if (list[i].contains("pid")) {
                    obList.add(list[i + 1]);
                }
                if (list[i].contains("price")) {
                    totalPrice = Double.valueOf(list[i + 1]);
                }
            }
            listView.setCellFactory(new Callback<ListView, ListCell>() {
                @Override
                public ListCell call(ListView param) {
                    return new ListCellProducts();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setItems(obList);
        totalText.setText(String.valueOf(totalPrice));
    }
}
