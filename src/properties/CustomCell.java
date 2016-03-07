package properties;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

//A custom Cell class for the listView in ExitView.
    public class CustomCell extends ListCell<String> {

        BorderPane borderpane = new BorderPane();
        Label productName = new Label();
        Label productTotalPrice = new Label();

        public CustomCell() {
            borderpane.setLeft(productName);
            borderpane.setRight(productTotalPrice);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item.length() < 50) {
                setGraphic(null);

            }
            else {
                productName.setText(item.substring(0, 50));
                productTotalPrice.setText(item.substring(50, item.length()));
                setGraphic(borderpane);
            }
        }
    }

