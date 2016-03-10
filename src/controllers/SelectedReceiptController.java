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
import java.util.ResourceBundle;

public class SelectedReceiptController implements Initializable {

    static Order o;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    @FXML
    private Button backButton;
    @FXML
    private ListView<ShoppingItem> listView;
    @FXML
    private Text totalText;

    public static void setOrder(Order order) {
        o = order;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewChanger vc = new ViewChanger();
        backButton.setOnAction(e -> {
            try {
                vc.changeScene(DataHolder.iMat.getContent(), "/fxml/Receipts.fxml");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        listView.setCellFactory(new Callback<ListView<ShoppingItem>, ListCell<ShoppingItem>>() {
            @Override
            public ListCell<ShoppingItem> call(ListView<ShoppingItem> param) {
                return new ListCellProducts();
            }
        });
        ObservableList<ShoppingItem> items = FXCollections.observableArrayList(o.getItems());
        listView.setItems(items);

        double sum = 0;

        for (ShoppingItem shi : o.getItems()) {
            sum = sum + (shi.getProduct().getPrice() * shi.getAmount());
        }
        totalText.setText(String.valueOf(sum));

    }

}
