package grafando.Controller;

import grafando.View.ConnectVertexView;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;
    MainScreenController delegate;

    ConnectVertexController(Stage primaryStage, MainScreenController delegate) {
        this.primaryStage = primaryStage;
        this.view = new ConnectVertexView(this.primaryStage);
        this.delegate = delegate;
        confirmButtonAction();
        view.positioningPopupInsideParentStage();
    }

    private void confirmButtonAction() {
        view.getConfirmButton().setOnAction(e ->{
            callDelegateDrawEdges();
            view.getPopUpStage().close();
        });
    }

    private void callDelegateDrawEdges() {
        delegate.createEdge(view.getStartingVertex(), view.getFinalVertex());
    }

}
