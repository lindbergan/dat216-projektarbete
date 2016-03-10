package controllers;


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
import properties.ViewChanger;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReceiptsController implements Initializable {

    @FXML
    ListView<Order> listView;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Order> ror = FXCollections.observableArrayList(handler.getOrders());

        listView.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
            @Override
            public ListCell<Order> call(ListView<Order> param) {
                return new ListCellReceipts();
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                SelectedReceiptController.setOrder(newValue);
                ViewChanger vc = new ViewChanger();
                try {
                    vc.changeScene(DataHolder.iMat.getContent(), "/fxml/SelectedReceipt.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setItems(ror);

    }

}