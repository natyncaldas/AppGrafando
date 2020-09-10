package grafando.Controller;

import grafando.View.ConnectVertexView;
import javafx.stage.Stage;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;

    ConnectVertexController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new ConnectVertexView(this.primaryStage);
    }

}
