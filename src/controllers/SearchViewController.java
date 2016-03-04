package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Razmus on 2016-03-03.
 */
public class SearchViewController implements Initializable {
    IMatDataHandler handler = IMatDataHandler.getInstance();
    private String input;
    @FXML
    GridPane gridRazz;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Product> products = handler.getProducts();
        try{
            Properties prop = new Properties();
            InputStreamReader in = new FileReader("search.txt");
            prop.load(in);
            input = prop.getProperty("input");
        }
        catch(Exception ex){
            ex.getStackTrace();
        }
        int column = 0;
        int row = 0;
        int changeRow = 0;
        for(int i = 0; i<products.size(); i++){
            if (products.get(i).getName().toLowerCase().contains(input)){
                Button newButton = new Button();
                newButton.setPrefWidth(200);
                newButton.setPrefHeight(240);
                String url = "/products/images/" + products.get(i).getImageName();
                ImageView img = new ImageView(new Image(url));
                img.setFitWidth(newButton.getPrefWidth());
                img.setFitHeight(newButton.getPrefHeight()*0.6);
                img.setEffect(new DropShadow(8, Color.BEIGE));
                newButton.setGraphic(img);
                gridRazz.add(newButton,column,changeRow);
                column++;
                row++;
                if(row == 4){
                    changeRow++;
                    row = 0;
                    column = 0;
                }
            }
        }
    }
}
