package grafando.Controller;

import grafando.View.ConnectVertexView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;

    ConnectVertexController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.view = new ConnectVertexView(this.primaryStage);
        confirmButtonAction();
        view.positioningPopupInsideParentStage();
    }

    private void confirmButtonAction() {
        view.getConfirmButton().setOnAction(e ->{
            view.getPopUpStage().close();
            System.out.println("Apertou.");
        });
    }

}
