package grafando.Controller;

import grafando.Model.MainGraphModel;
import grafando.View.ConnectVertexView;
import javafx.stage.Stage;

import java.util.HashSet;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;
    MainScreenController mainScreenController;
    MainGraphModel graphModel;

    ConnectVertexController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.graphModel = MainGraphModel.getInstance();

        HashSet<Integer> vertexesCurrentlyOnScreen = graphModel.getVertices().getConjunto();
        this.view = new ConnectVertexView(this.primaryStage, vertexesCurrentlyOnScreen);
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
        Integer initialVertex = (Integer) view.getStartingVertex().getValue();
        Integer finalVertex = (Integer) view.getFinalVertex().getValue();
        mainScreenController.callDrawEdgeOnView(initialVertex,finalVertex);
    }

}
