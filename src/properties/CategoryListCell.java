package properties;

import controllers.DataHolder;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class CategoryListCell extends ListCell<String> {

    @Override
    public void updateItem(String item, boolean empty) {
        if (item != null) {
            super.updateItem(item, empty);
            Label l = new Label(item);

            String url = "/images/" + item + ".png/";
            Image image = new Image(url);
            ImageView l2 = new ImageView(image);
            l2.setFitHeight(46);
            l2.setFitWidth(75);

            l.setFont(Font.font("Verdana", 15));

            StackPane sp = new StackPane(l, l2);
            sp.setPrefWidth(DataHolder.iMat.getListView().getWidth() * 0.75);
            sp.setPrefHeight(DataHolder.iMat.getListView().getHeight() * 0.075);

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

}
