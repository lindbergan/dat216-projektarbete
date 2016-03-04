package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jolo on 3/1/16.
 */
public class ViewChanger {

    //change Scene
    public void changeScene(AnchorPane oldView, String newViewUrl) throws IOException {

        AnchorPane confirmationView = FXMLLoader.load(getClass().getResource(newViewUrl));
        oldView.getChildren().setAll(confirmationView);
    }

    //change Stage
    public void changeStage(ActionEvent event, AnchorPane pane, String url) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(parent, pane.getWidth(), pane.getHeight()); //Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight()
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    //Change stage avd override to specified Resolution : 1280x720
    public void changeStageOverride(ActionEvent event, String url) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(parent, parent.prefWidth(1280.0), parent.prefHeight(720.0));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
