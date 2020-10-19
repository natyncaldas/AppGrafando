package grafando.Controller;

import grafando.Model.GraphModel;
import grafando.View.ConnectVertexView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;

public class ConnectVertexController {

    Stage primaryStage;
    ConnectVertexView view;
    MainScreenController mainScreenController;
    GraphModel graphModel;

    ConnectVertexController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.graphModel = GraphModel.getInstance();

        HashSet<Integer> vertexesCurrentlyOnScreen = graphModel.getVertexes().getVertexSet();
        this.view = new ConnectVertexView(this.primaryStage, vertexesCurrentlyOnScreen);
        this.mainScreenController = mainScreenController;
        confirmButtonAction();
        view.positioningPopupInsideParentStage();
    }

    private void confirmButtonAction() {

        EventHandler<MouseEvent> mousePressed = e ->{
            boolean emptyStart = view.getStartingVertex().getSelectionModel().isEmpty();
            boolean emptyFinal = view.getFinalVertex().getSelectionModel().isEmpty();
            if (emptyStart || emptyFinal) {
                view.getStartingVertex().setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
                view.getFinalVertex().setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
            } else{
                callDelegateDrawEdges();
                view.getPopUpStage().close();
            }
        };

        EventHandler<MouseEvent> mouseReleased = e ->{
            view.getStartingVertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
            view.getFinalVertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
        };

        view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
        view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleased);
    }

    private void callDelegateDrawEdges() {
        Integer initialVertex, finalVertex;
        initialVertex = view.getStartingVertex().getValue();
        finalVertex = view.getFinalVertex().getValue();
        if (!graphModel.hasEdge(initialVertex, finalVertex) && !initialVertex.equals(finalVertex)) {
            mainScreenController.callDrawEdgeOnView(initialVertex,finalVertex);
            System.out.println("Entrou.");
        }
    }
}
