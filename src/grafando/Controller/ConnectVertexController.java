package grafando.Controller;

import grafando.View.ConnectVertexView;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;

    ConnectVertexController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new ConnectVertexView(this.primaryStage);
    }

}
