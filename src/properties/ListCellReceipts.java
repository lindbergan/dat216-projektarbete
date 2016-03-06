package properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.FileReader;

public class ListCellReceipts extends ListCell<String> {

    static int j = 0;
    BufferedReader reader;
    String[] temp;
    ObservableList<String> date = FXCollections.observableArrayList();
    ObservableList<String> fname = FXCollections.observableArrayList();
    ObservableList<String> lname = FXCollections.observableArrayList();
    ObservableList<String> quantity = FXCollections.observableArrayList();
    ObservableList<String> price = FXCollections.observableArrayList();

    public ListCellReceipts() {
        GridPane gp = new GridPane();

        Text name = new Text();
        Text date = new Text();
        Text quantity = new Text();
        Text price = new Text();

        gp.add(name, 0, 0, 1, 1);
        gp.add(date, 1, 0, 1, 1);
        gp.add(quantity, 2, 0, 1, 1);
        gp.add(price, 3, 0, 1, 1);

        gp.getColumnConstraints().add(0, new ColumnConstraints(300));
        gp.getColumnConstraints().add(1, new ColumnConstraints(400));
        gp.getColumnConstraints().add(2, new ColumnConstraints(150));
        gp.getColumnConstraints().add(3, new ColumnConstraints(150));
        setInfo(name, date, quantity, price);
        gp.setVisible(true);
        setVisible(true);
        setGraphic(gp);
    }

    public void setInfo(Text n, Text d, Text q, Text p) {
        try {
            reader = new BufferedReader(new FileReader("receipts.txt"));
            temp = reader.readLine().split(";");

            for (int i = 0; i < temp.length; i++)  {
                switch (temp[i]) {
                    case "date" : date.add(temp[i + 1]);
                        break;
                    case "name" : fname.add(temp[i + 1]);
                        lname.add(temp[i + 2]);
                        break;
                    case "price" : price.add(temp[i + 1]);
                        break;
                    case "quantity" : quantity.add(temp[i + 1]);
                        break;
                    default: break;
                }
            }
            int nrReceipts = date.size() - 1;
            if (j <= nrReceipts) {
                d.setText(date.get(j));
                n.setText(fname.get(j) + " " + lname.get(j));
                q.setText(quantity.get(j));
                p.setText(price.get(j));
                j++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
