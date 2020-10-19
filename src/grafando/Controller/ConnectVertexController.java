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

    // Começa recebendo uma referência ao stage anterior
    // recebe também o controller que o apresenta, para que possa chamar seus métodos na
    // view principal
    ConnectVertexController(Stage primaryStage, MainScreenController mainScreenController) {
        this.primaryStage = primaryStage;
        this.graphModel = GraphModel.getInstance();

        // Para que se preencha as comboboxes é necessário saber a quantidade de vertexes
        // que estão na tela
        HashSet<Integer> vertexesCurrentlyOnScreen = graphModel.getVertexes().getVertexSet();
        this.view = new ConnectVertexView(this.primaryStage, vertexesCurrentlyOnScreen);
        this.mainScreenController = mainScreenController;
        confirmButtonAction();
        view.positioningPopupInsideParentStage();
    }

    private void confirmButtonAction() {
        // Funcionamento do botão de confirmação da popup:
        // ele checa se ao ser clicado as comboboxes tinham algo dentro
        // caso uma delas esteja vazia, uma borda vermelha é desenhada
        // para representar o erro;
        // se não, apenas desenha a aresta;
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
        // Retorna as bordas das comboboxes que podem ter sido mudadas para vermelho;
        EventHandler<MouseEvent> mouseReleased = e ->{
            view.getStartingVertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
            view.getFinalVertex().setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
        };

        view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressed);
        view.getConfirmButton().addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleased);
    }

    // Pega os valores da comboboxes e passa para um método na
    // main screen controller, que, por possuir o pane, possui a função
    // de desenhar;
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
