package properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileReader;

public class ListCellProducts extends ListCell {

    static int j = 0;
    BufferedReader reader;
    String[] temp;
    ObservableList<String> name = FXCollections.observableArrayList();
    ObservableList<String> quantity = FXCollections.observableArrayList();
    ObservableList<String> price = FXCollections.observableArrayList();
    ObservableList<String> total = FXCollections.observableArrayList();

    public ListCellProducts() {
        GridPane gp = new GridPane();

        Text name = new Text();
        Text quantity = new Text();
        Text price = new Text();
        Text total = new Text();

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
        setInfo(name, quantity, price, total);
        gp.setVisible(true);
        setVisible(true);
        setGraphic(gp);
    }

    public void setInfo(Text n, Text q, Text p, Text t) {
        try {
            reader = new BufferedReader(new FileReader("receipts.txt"));
            temp = reader.readLine().split(";");

            for (int i = 0; i < temp.length; i++)  {
                switch (temp[i]) {
                    case "pid" : name.add(temp[i + 1]);
                        break;
                    case "pamount" : quantity.add(temp[i + 1]);
                        break;
                    case "pprice" : price.add(temp[i + 1]);
                        break;
                    case "ptotal" : total.add(temp[i + 1]);
                        break;
                    default: break;
                }
            }
            int nrReceipts = name.size() - 1;
            if (j <= nrReceipts) {
                n.setText(name.get(j));
                q.setText(quantity.get(j));
                p.setText(price.get(j));
                t.setText(total.get(j));
                j++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
