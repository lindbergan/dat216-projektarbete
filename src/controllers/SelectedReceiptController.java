package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import properties.ListCellProducts;
import properties.ListCellReceipts;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SelectedReceiptController implements Initializable {

    @FXML private Button backButton;
    @FXML private Button manageButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    @FXML private ListView listView;
    @FXML private Text totalText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
