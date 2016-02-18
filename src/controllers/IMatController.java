package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.net.URL;
import java.util.*;

public class IMatController implements Initializable {

    @FXML private ToggleButton BP1Toggle1;
    @FXML private ToggleButton BP1Toggle2;
    @FXML private TableView tableView1;
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane2.setVisible(false);
        BP1Toggle1.setOnAction(event -> {
            BP1Toggle2.setSelected(false);
            pane2.setVisible(false);
            pane1.setVisible(true);
        });
        BP1Toggle2.setOnAction(event -> {
            BP1Toggle1.setSelected(false);
            pane2.setVisible(true);
            pane1.setVisible(false);

        });

        ObservableList<ProductCategory> categories = FXCollections.observableArrayList(ProductCategory.values());
        tableView1.setItems(categories);
    }
}
