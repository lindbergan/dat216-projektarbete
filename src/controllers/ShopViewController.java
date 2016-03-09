package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ShopViewController implements Initializable {

    private final String[] categoryArr = {"Baljväxter", "Bröd", "Frukt och grönt", "Skafferi", "Sötsaker och drycker", "Fisk", "Kött", "Mejeri", "Nötter och frön", "Pasta, potatis och ris", "Rotfrukter"};
    SelectedCategoryMenuController categoryHandler = new SelectedCategoryMenuController();
    @FXML
    private AnchorPane shopMenuAP;
    @FXML
    private ImageView shopView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImage();
    }

    public void setImage() {
        Image image = new Image("/images/IMat-butik.png/");
        shopView.setImage(image);
    }

    @FXML
    public void buttonAction(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            Button button = (Button) e.getSource();
            if (!(button.getText() == null || button.getText().equals(""))) {
                try {
                    /*Properties prop = new Properties();

                    FileOutputStream out = new FileOutputStream("products.txt");
                    prop.setProperty("category", button.getText());
                    prop.store(out, null);
                    setPane();*/
                    int index = -1;
                    for (int i = 0; (i < categoryArr.length) && (index == -1); i++) {
                        if (categoryArr[i].equals(button.getText())) {
                            index = i;
                        }
                    }
                    if (index != -1) {
                        DataHolder.iMat.selectCategory(index);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void setPane() {
        try {
            AnchorPane e = FXMLLoader.load(getClass().getResource("/fxml/SelectedCategoryMenu.fxml/"));
            shopMenuAP.getChildren().setAll(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
