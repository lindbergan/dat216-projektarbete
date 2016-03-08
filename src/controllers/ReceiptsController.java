package controllers;


import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.util.Callback;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import properties.ListCellReceipts;
import properties.ViewChanger;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class ReceiptsController implements Initializable {

    public static int variable = 0;

    @FXML ListView<Order> listView;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Order> orderList = handler.getOrders();

        ObservableList<Order> ror = FXCollections.observableArrayList();

        ror.addAll(orderList.stream().collect(Collectors.toList()));
        System.out.println(ror.size());

        listView.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
            @Override
            public ListCell<Order> call(ListView<Order> param) {
                return new ListCellReceipts();
            }
        });
        variable = 0;
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                SelectedReceiptController.setOrder(newValue);
                ViewChanger vc = new ViewChanger();
                try {
                    System.out.println("RC " + newValue.getDate().toString());
                    vc.changeScene(IMatController.contentProperty, "/fxml/SelectedReceipt.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setItems(ror);

    }

}