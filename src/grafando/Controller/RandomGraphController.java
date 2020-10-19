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

    // Inicializa o controller com referencia para o controller pai, além
    // de referenciar o stage principal
    RandomGraphController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.graphModel = GraphModel.getInstance();
        ArrayList<Integer> quantidade = new ArrayList<Integer>();

        // Adiciona de 5 a 25 para se selecionar nas comboboxes
        for (int i=5; i<26; i++){
            quantidade.add(i);
        }
        this.randomView = new RandomGraphView(this.primaryStage, quantidade);
        this.mainScreenController = mainScreenController;
        confirmButtonAction();
        randomView.positioningPopupInsideParentStage();
    }
    
    private void confirmButtonAction() {
        // Caso nenhum número tenha sido selecionado, não é possível desenhar o
        // grafo.
        // Após esse teste é feito uma geração de um grafo aleatório
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

        // Retorna as bordas para a cor inicial, no caso de terem sido alteradas
        EventHandler<MouseEvent> mouseReleased = e ->{
            randomView.getNumberVertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
        };

        randomView.getConfirmButton().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
        randomView.getConfirmButton().addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleased);
    }
}
