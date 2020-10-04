package grafando.Controller;


import grafando.Model.GraphModel;

import grafando.View.RandomGraphView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;


public class RandomGraphController {
    Stage primaryStage;
    RandomGraphView random_view;
    MainScreenController mainScreenController;
    GraphModel graphModel;

    RandomGraphController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.graphModel = GraphModel.getInstance();
        ArrayList<Integer> quantidade = new ArrayList<Integer>();
        for (int i=5; i<26; i++){
            quantidade.add(i);
        }
        this.random_view = new RandomGraphView(this.primaryStage, quantidade);
        this.mainScreenController = mainScreenController;
        confirmButtonAction();
        random_view.positioningPopupInsideParentStage();
    }
    private void confirmButtonAction() {

        EventHandler<MouseEvent> mousePressed = e ->{
            boolean emptynumber = random_view.getNumber_vertex().getSelectionModel().isEmpty();
            if (emptynumber) {
                random_view.getNumber_vertex().setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
            }else{
                int graphSize = random_view.getNumber_vertex().getValue();
                try {
                    mainScreenController.confirmRandomGraph(graphSize);
                } catch (Exception exception) {
                    exception.getLocalizedMessage();
                }
                random_view.getRandomPopUpStage().close();
            }
        };

        EventHandler<MouseEvent> mouseReleased = e ->{
            random_view.getNumber_vertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
        };

        random_view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
        random_view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleased);
    }
}
