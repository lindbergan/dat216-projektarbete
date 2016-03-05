package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Lisa on 2016-03-04.
 */
public class DetailViewController implements Initializable {

    @FXML
    private AnchorPane detailViewAP;
    @FXML
    private ImageView productImage;
    @FXML
    private ImageView backImage;
    @FXML
    private ImageView favImage;
    @FXML
    private ImageView listImage;
    @FXML
    private ImageView cartImage;
    @FXML
    private Button favButton;
    @FXML
    private Label price;
    @FXML
    private Label productName;
    @FXML
    private Label suffix;
    @FXML
    private TextField amount;
    @FXML
    private TextArea productDesc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStaticImages() {
        Image product = new Image("/images/Bröd.png/");
        Image back = new Image("/images/Fisk.png/");
        Image list = new Image("/images/Baljväxter.png/");
        Image cart = new Image("/images/Frukt.png/");
        productImage.setImage(product);
        backImage.setImage(back);
        listImage.setImage(list);
        cartImage.setImage(cart);
    }

    public void setFavButton(boolean isSelected) {
        Image fav;
        if (isSelected) {
            favButton.setText("I Favoriter");
            fav = new Image("/images/Kött.png/");
        } else {
            favButton.setText("Lägg till i Favoriter");
            fav = new Image("/images/Mejeri.png/");
        }
        favImage.setImage(fav);
    }
}
