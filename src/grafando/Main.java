package grafando;

import grafando.Controller.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //Launcher da aplicação em JavaFX a partir do Controller
    @Override
    public void start(Stage primaryStage) throws Exception{
        MainScreenController controller = new MainScreenController(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Grafando");
        primaryStage.setScene(new Scene(controller.getView().getRoot(), 600, 430));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

