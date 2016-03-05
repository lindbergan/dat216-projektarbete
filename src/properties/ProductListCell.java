package properties;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Created by lindberg on 2016-03-05.
 */
public class ProductListCell extends ListCell<String> {

    Label name;
    Label quantity;
    Label price;

    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        System.out.println(item);
        System.out.println(empty);
        GridPane gp = new GridPane();
        name = new Label();
        quantity = new Label();
        price = new Label();

        ImageView imageView = new ImageView();
        //Image image = new Image();

        gp.add(imageView, 0, 0, 1, 1);
        gp.add(name, 0, 1, 1, 1);
        gp.add(quantity, 2, 0, 1, 1);
        gp.add(price, 3, 0, 1, 1);

        ColumnConstraints cc1 = new ColumnConstraints(75, 75, 50);
        ColumnConstraints cc2 =  new ColumnConstraints(100);
        ColumnConstraints cc3 = new ColumnConstraints(50);

        gp.getColumnConstraints().add(0, cc1);
        gp.getColumnConstraints().add(1, cc2);
        gp.getColumnConstraints().add(2, cc3);
        gp.getColumnConstraints().add(3, cc3);
        setGraphic(gp);
    }

    public void setInfo(String name, String quantity, String price) {

    }

}
