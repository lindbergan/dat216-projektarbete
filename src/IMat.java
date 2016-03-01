import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class IMat extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final IMatDataHandler handler = IMatDataHandler.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/IMat.fxml"));

        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        primaryStage.setTitle("IMat");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                handler.shutDown();
                try {
                    Properties prop = new Properties();

                    FileOutputStream out = new FileOutputStream("currentView.txt");
                    prop.setProperty("URL", "/fxml/shopView.fxml/");
                    prop.store(out, null);


                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args);}
    
}
