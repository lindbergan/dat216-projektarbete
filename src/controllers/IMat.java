package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class IMat extends Application {

    private Stage primaryStage;
    private StackPane mainLayout;
    private BorderPane borderPane1;
    private BorderPane borderPane2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("iMat - Framtidens mat i din dator");
        showMainView();
        initAllPanes();
    }


    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("IMat.fxml"));
        this.mainLayout = loader.load();
        Scene scene = new Scene(this.mainLayout);
        this.primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        this.primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        borderPane1 = (BorderPane) mainLayout.getChildren().get(0);
    }

    private void initAllPanes() throws IOException {
        initBP1HeaderPane();
        initBP1CenterPane();
    }

    private void initBP1HeaderPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BP1Header.fxml"));
        AnchorPane borderPane1Header = loader.load();
        this.borderPane1.setTop(borderPane1Header);
    }

    private void initBP1CenterPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BP1Center.fxml"));
        AnchorPane borderPane1Center = loader.load();
        this.borderPane1.setCenter(borderPane1Center);
    }
}
