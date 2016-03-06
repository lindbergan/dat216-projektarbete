package properties;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.Customer;

public class Receipt extends GridPane {

    String name;
    String date;
    int quantity;
    double price;
    Customer customer;

    public Receipt() {

        Text name = new Text("Test1");
        Text date = new Text("Test2");
        Text quantity = new Text("Test3");
        Text price = new Text("Test4");

        add(name, 0, 0, 1, 1);
        add(date, 1, 0, 1, 1);
        add(quantity, 2, 0, 1, 1);
        add(price, 3, 0, 1, 1);

    }

}
