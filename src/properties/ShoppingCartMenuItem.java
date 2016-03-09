package properties;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ShoppingCartMenuItem extends MenuItem {

    public ShoppingCartMenuItem(MenuButton parent, String name, double amount, String suffix, double price) {

        GridPane gp = new GridPane();

        Text n = new Text(name);
        Text a = new Text(String.valueOf(amount));
        Text s = new Text(suffix);
        Text p = new Text(String.valueOf(price));

        gp.add(n, 0, 0, 1, 1);
        gp.add(a, 1, 0, 1, 1);
        gp.add(s, 2, 0, 1, 1);
        gp.add(p, 3, 0, 1, 1);

        gp.getColumnConstraints().add(0, new ColumnConstraints(75));
        gp.getColumnConstraints().add(1, new ColumnConstraints(25));
        gp.getColumnConstraints().add(2, new ColumnConstraints(25));
        gp.getColumnConstraints().add(3, new ColumnConstraints(25));

        setGraphic(gp);
    }

}
