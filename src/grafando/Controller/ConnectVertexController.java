package grafando.Controller;

import grafando.View.ConnectVertexView;
import javafx.stage.Stage;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;
    MainScreenController mainScreenController;

    ConnectVertexController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.view = new ConnectVertexView(this.primaryStage);
        this.mainScreenController = mainScreenController;
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
        mainScreenController.callDrawEdgeOnView(0,1);
    }

}
