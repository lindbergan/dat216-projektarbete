package properties;

import controllers.IMatController;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Created by lindberg on 2016-03-05.
 */
public class NewShoppingList {
    public IMatController handler = new IMatController();
    private AnchorPane pane;
    private VBox vboxer;
    private MenuButton menuButton;
    private MenuItem menuItem;

    public NewShoppingList(AnchorPane parent, MenuButton mb, MenuItem mi) {
        pane = parent;
        menuButton = mb;
        menuItem = mi;
        nameNewList();
    }

    public void nameNewList() {
        Label l = new Label("Skapa en ny lista");
        l.setFont(Font.font(16));

        TextField tf = new TextField();
        tf.setPromptText("Vänligen ange namn");

        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> newList(tf.getText()));

        Button cancelButton = new Button("Avbryt");
        cancelButton.setOnAction(e -> cancel());

        VBox vbox = new VBox(l, tf);
        vbox.setSpacing(10);
        HBox hbox = new HBox(cancelButton, okButton);
        hbox.setSpacing(10);
        VBox vboxLayout = new VBox(vbox, hbox);
        vboxLayout.setSpacing(10);

        vboxLayout.setPrefSize(250, 100);
        vboxLayout.getStyleClass().add("newShoppingList");
        vboxLayout.setLayoutX(150);
        vboxer = vboxLayout;
        pane.getChildren().add(vboxLayout);
    }

    public void newList(String name) {
        remove();
        menuButton.getItems().add(new MenuItem(name));
        handler.initShoppingCartLists();
    }

    public void cancel() {
        remove();
        menuButton.getItems().add(menuItem);
    }

    public void remove() {
        pane.getChildren().remove(vboxer);
    }


}
