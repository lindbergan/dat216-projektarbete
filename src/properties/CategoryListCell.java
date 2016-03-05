package properties;

import controllers.IMatController;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class CategoryListCell extends ListCell<String> {
    IMatController handler = new IMatController();

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        Label l = new Label(item);
        Label l2 = new Label();

        l.setFont(Font.font("Verdana", 15));

        StackPane sp = new StackPane(l, l2);
        sp.setPrefWidth(handler.getListProperty().getWidth() * 0.75);
        sp.setPrefHeight(handler.getListProperty().getHeight() * 0.075);

        GridPane gp = new GridPane();
        gp.add(l2, 0, 0, 1, 1);
        gp.add(l, 1, 0, 1, 1);
        gp.setHgap(10);

        ColumnConstraints cc1 = new ColumnConstraints(75);

        gp.getColumnConstraints().add(0, cc1);
        sp.getChildren().add(gp);
        sp.setId("sp");
        setGraphic(sp);
    }

}
