package grafando;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //Launcher da aplicação em JavaFX a partir do Controller
    @Override
    public void start(Stage primaryStage) throws Exception{
        MainScreenController controller = new MainScreenController();
        primaryStage.setResizable(false);
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(350);
        primaryStage.setTitle("Graph");
        primaryStage.setScene(new Scene(controller.getView().getRoot(), 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
