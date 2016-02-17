import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class IMat extends Application {

    private Stage primaryStage;
    private StackPane mainLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("iMat - Framtidens mat i din dator");
        showMainView();
    }

    private void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(IMat.class.getResource("IMat.fxml"));
        this.mainLayout = loader.load();

        Scene scene = new Scene(this.mainLayout);
        this.primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        this.primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
}
