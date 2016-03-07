package controllers;


import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import properties.ListCellReceipts;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReceiptsController implements Initializable {

    @FXML ListView listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("receipts.txt"));
            String[] arr = reader.readLine().split(";");
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i].contains("date")) {
                    list.add(arr[i + 1]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCellReceipts();
            }
        });
        listView.setItems(list);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                properties.ViewChanger vc = new properties.ViewChanger();
                try {
                    vc.changeScene(IMatController.contentProperty, "/fxml/SelectedReceipt.fxml/");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}