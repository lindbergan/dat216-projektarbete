package properties;


import controllers.ReceiptsController;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;


public class ListCellReceipts extends ListCell<Order> {

    IMatDataHandler handler = IMatDataHandler.getInstance();
    Customer c = handler.getCustomer();

    @Override
    public void updateItem(Order o, boolean empty) {
        super.updateItem(o, empty);

        if (empty || o == null) {
            setText(null);
            setGraphic(null);
        }

        else {
            GridPane gp = new GridPane();

            Text name = new Text(c.getFirstName() + " " + c.getLastName());
            Text date = new Text(o.getDate().toString());
            Text quantity = new Text(String.valueOf(o.getItems().size()));
            double sum = 0;
            for (int i = 0; i < o.getItems().size(); i++) {
                sum = sum + (o.getItems().get(i).getProduct().getPrice() * o.getItems().get(i).getAmount());
            }

            Text price = new Text(String.format("%.2f", sum));

            gp.add(name, 0, 0, 1, 1);
            gp.add(date, 1, 0, 1, 1);
            gp.add(quantity, 2, 0, 1, 1);
            gp.add(price, 3, 0, 1, 1);

            gp.getColumnConstraints().add(0, new ColumnConstraints(300));
            gp.getColumnConstraints().add(1, new ColumnConstraints(350));
            gp.getColumnConstraints().add(2, new ColumnConstraints(250));
            gp.getColumnConstraints().add(3, new ColumnConstraints(150));
            gp.setVisible(true);
            setVisible(true);
            setGraphic(gp);
        }

    }

}
