package properties;

import controllers.SelectedReceiptController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public class ListCellProducts extends ListCell {

    public ListCellProducts(Order o, ObservableList<ShoppingItem> items) {


        if (SelectedReceiptController.variable < items.size()) {
            GridPane gp = new GridPane();

            Text name = new Text(items.get(SelectedReceiptController.variable).getProduct().getName());
            Text quantity = new Text(String.valueOf(items.get(SelectedReceiptController.variable).getAmount()));
            Text price = new Text(String.valueOf(items.get(SelectedReceiptController.variable).getProduct().getPrice()));
            Text total = new Text(String.valueOf(items.get(SelectedReceiptController.variable).getTotal()));

            gp.add(name, 1, 0, 1, 1);
            gp.add(quantity, 2, 0, 1, 1);
            gp.add(price, 3, 0, 1, 1);
            gp.add(total, 4, 0, 1, 1);

            gp.getColumnConstraints().add(0, new ColumnConstraints(75));
            gp.getColumnConstraints().add(1, new ColumnConstraints(275));
            gp.getColumnConstraints().add(2, new ColumnConstraints(150));
            gp.getColumnConstraints().add(3, new ColumnConstraints(150));
            gp.getColumnConstraints().add(4, new ColumnConstraints(180));
            gp.getColumnConstraints().add(5, new ColumnConstraints(150));
            gp.setVisible(true);
            setVisible(true);
            setGraphic(gp);
        }
        SelectedReceiptController.variable++;
    }

}
