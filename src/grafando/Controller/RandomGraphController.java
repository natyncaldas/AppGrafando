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
    RandomGraphView randomView;
    MainScreenController mainScreenController;
    GraphModel graphModel;

    RandomGraphController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.graphModel = GraphModel.getInstance();
        ArrayList<Integer> quantidade = new ArrayList<Integer>();
        for (int i=5; i<26; i++){
            quantidade.add(i);
        }
        this.randomView = new RandomGraphView(this.primaryStage, quantidade);
        this.mainScreenController = mainScreenController;
        confirmButtonAction();
        randomView.positioningPopupInsideParentStage();
    }
    
    private void confirmButtonAction() {

        EventHandler<MouseEvent> mousePressed = e ->{
            boolean emptynumber = randomView.getNumberVertex().getSelectionModel().isEmpty();
            if (emptynumber) {
                randomView.getNumberVertex().setBorder(new Border(new BorderStroke(Color.ORANGERED, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
            }else{
                int graphSize = randomView.getNumberVertex().getValue();
                try {
                    mainScreenController.confirmRandomGraph(graphSize);
                } catch (Exception exception) {
                    exception.getLocalizedMessage();
                }
                randomView.getRandomPopUpStage().close();
            }
        };

        EventHandler<MouseEvent> mouseReleased = e ->{
            randomView.getNumberVertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
        };

        randomView.getConfirmButton().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
        randomView.getConfirmButton().addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleased);
    }
}
