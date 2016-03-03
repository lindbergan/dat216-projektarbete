package properties;

import controllers.IMatController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CategoryListCell extends ListCell<String> {
    IMatController handler = new IMatController();
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        Label l = new Label(item);
        Label l2 = new Label();

        l2.setGraphic(new Rectangle(75, 50, Color.AQUAMARINE));

        StackPane sp = new StackPane(l, l2);
        sp.setPrefWidth(handler.getListProperty().getWidth() * 0.75);
        sp.setPrefHeight(handler.getListProperty().getHeight()*0.075);
        sp.setAlignment(l, Pos.CENTER_LEFT);
        sp.setAlignment(l2, Pos.CENTER_RIGHT);
        setPadding(new Insets(5, 0, 0, 0));
        setGraphic(sp);
    }

}
