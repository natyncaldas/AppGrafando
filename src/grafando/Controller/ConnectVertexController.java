package grafando.Controller;

import grafando.Model.MainGraphModel;
import grafando.View.ConnectVertexView;
import grafando.View.Vertex;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
        EventHandler<MouseEvent> eventHandler = e ->{
            boolean emptyStart = view.getStartingVertex().getSelectionModel().isEmpty();
            boolean emptyFinal = view.getFinalVertex().getSelectionModel().isEmpty();
            if (emptyStart || emptyFinal) {
                view.getStartingVertex().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                view.getFinalVertex().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            } else{
                callDelegateDrawEdges();
                view.getPopUpStage().close();
            }
        };
        view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    private void callDelegateDrawEdges() {
        Integer initialVertex, finalVertex;
        try {
            initialVertex = (Integer) view.getStartingVertex().getValue();
            finalVertex = (Integer) view.getFinalVertex().getValue();
            mainScreenController.callDrawEdgeOnView(initialVertex,finalVertex);
        } catch(NullPointerException e) {
            System.out.println("ERROR: NULL POINTER");
        }
    }

}
