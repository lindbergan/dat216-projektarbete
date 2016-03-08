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
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ReceiptsController implements Initializable {

    @FXML ListView listView;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Order> orderList = handler.getOrders();

        ObservableList<Order> ror = FXCollections.observableArrayList();

        for (Order o : orderList) {
            ror.add(o);
        }

        listView.setItems(ror);

        listView.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
            @Override
            public ListCell<Order> call(ListView<Order> param) {
                return new ListCellReceipts();
            }
        });

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