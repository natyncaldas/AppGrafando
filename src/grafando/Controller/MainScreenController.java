package grafando.Controller;

import grafando.Model.DepthFirstSearch;
import grafando.Model.GraphModel;
import grafando.View.Edge;
import grafando.View.MainScreenView;
import grafando.View.Vertex;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainScreenController {
    //Declaração da View
    public Stage primaryStage;
    private MainScreenView view;
    public GraphModel graphModel;
    private ArrayList<DepthFirstSearch> searchExecution;
    private int currentState;

    public MainScreenController(Stage primaryStage) throws FileNotFoundException {
        //Salva referência ao stage principal
        this.primaryStage = primaryStage;
        //Instanciação da View
        this.setView(new MainScreenView());

        //Chamada de métodos com eventos para Nodes específicos
        this.colorPressedButton(this.view.getRunDFS(), this.view.getRandom(), this.view.getAddE(), this.view.getClear(), this.view.getNext(), this.view.getPrevious());
        this.drawVertex(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes());
        this.clearGraph(this.view.getDrawGraph(), this.view.getClear(), this.view.getVertexes());
        this.showDFSState(this.view.getNext(), this.view.getPrevious(), this.view.getRunDFS(), this.view.getStopDFS(),this.view.getAddV(), this.view.getDelete(), this.view.getAddE(), this.view.getClear(), this.view.getRandom());
        this.deleteElements(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes(), this.view.getEdges());

        this.graphModel = GraphModel.getInstance();
        this.openConnectVertexScreen();
        this.openRandomGraphScreen();
    }
    //Getter e Setter para View
    public MainScreenView getView() {
        return view;
    }

    public void setView(MainScreenView view) {
        this.view = view;
    }

    //Métodos para adcionar eventos em Nodes

    //*Completa
    public void colorPressedButton(Button... b){
        for (Button btn:b) {
            btn.setOnMousePressed(mouseEvent -> {
                final Color bg = btn.getGraphic() == null ? Color.SPRINGGREEN : Color.SLATEGRAY;
                btn.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
            }) ;
            btn.setOnMouseReleased(mouseEvent -> {
                final Color bg = btn.getGraphic() == null ? Color.LIGHTGRAY : null;
                btn.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
            });
        }
    }

    //*Completa
    public void drawVertex(Pane pane, ToggleGroup group, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e -> {

            if(pane.contains(e.getX() + 30, e.getY() + 30)
                    && !(e.getTarget() instanceof StackPane) && !(e.getTarget() instanceof Circle)
                    && !(e.getTarget() instanceof Text)){

                graphModel.addVertex(vertexArray.size());
                Vertex vertex = new Vertex();

                Circle circle = new Circle();
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                MainScreenView.styleVertexShape(circle);

                Text txt = new Text(""+vertexArray.size());
                MainScreenView.styleVertexText(txt);

                vertex.setVertex(new StackPane(), circle, txt);
                vertexArray.add(vertex);
                vertex.getVertex().setLayoutX(e.getX()-13);
                vertex.getVertex().setLayoutY(e.getY()-13);

                pane.getChildren().addAll(vertex.getVertex());
            }
        };
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle().getUserData() == "AddV") {
                pane.setCursor(Cursor.HAND);
                pane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }else{
                pane.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        });
    }

    public void showDFSState(Button b1, Button b2, Button run, Button stop, RadioButton toDisable, RadioButton toDisable2, Button... toDisable3){
        EventHandler<MouseEvent> eventHandler = e ->{
            if(e.getSource().equals(run)){
                b1.setOpacity(1);
                b2.setOpacity(1);
                b1.setDisable(false);
                toDisable.setDisable(true);
                toDisable2.setDisable(true);
                for (Button b:toDisable3) {
                    b.setDisable(true);
                }
                currentState = -1;
                executeDFS();
            }
            if (e.getSource().equals(stop)) {
                b1.setOpacity(0);
                b2.setOpacity(0);
                b1.setDisable(true);
                b2.setDisable(true);
                toDisable.setDisable(false);
                toDisable2.setDisable(false);
                for (Button b:toDisable3) {
                    b.setDisable(false);
                }
            }
            if (e.getSource().equals(b1)) {
                boolean isValidState = goToNextExecutionStep();
                if (!isValidState) { b1.setDisable(true); }
                if (isValidState) { b2.setDisable(false); }

            }
            if (e.getSource().equals(b2)) {
                boolean isValidState = goToPreviousExecutionStep();
                if (!isValidState) { b2.setDisable(true); }
                if (isValidState) { b1.setDisable(false); }

            }
        };
        stop.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        run.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        b1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        b2.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    //*Completa
    public void clearGraph(Pane pane, Button b, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e ->{
            pane.getChildren().clear();
            vertexArray.clear();
            graphModel.clearGraph();
        };
        b.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }


    //*Versão anterior estava retornando várias NullPointerExceptions; lambda de eventos para Edges bugava em alguns casos
    //TODO: verificar se operacões marcadas como "teste" resolvem o problema
    public void deleteElements(Pane pane, ToggleGroup group, ArrayList<Vertex> vertexArray, ArrayList<Edge> edgesArray){
        EventHandler<MouseEvent> eventHandler = e ->{

            ArrayList<Edge>deleted = new ArrayList<>();//teste

            for (Vertex v:vertexArray) {
                if(v.getVertex().getChildren().contains(e.getTarget())) {

                    for (Edge l:v.getConnectedEdges()) {
                        graphModel.removeEdge(l.getInitialVertex(), l.getFinalVertex());
                        deleted.add(l);//teste

                    }
                    for (Edge l:deleted){
                        int index  = vertexArray.indexOf(v) == l.getInitialVertex() ? l.getFinalVertex() : l.getInitialVertex();
                        vertexArray.get(index).getConnectedEdges().remove(l);//teste
                    }

                    graphModel.removeVertex(vertexArray.indexOf(v));
                    pane.getChildren().removeAll(v.getConnectedEdges());
                    edgesArray.removeAll(v.getConnectedEdges());//teste
                    v.getVertex().getChildren().removeAll();
                    pane.getChildren().remove(v.getVertex());
                    v.delete();
                    break;
                }
            }
            deleted = new ArrayList<>();//teste

            for (Edge l: edgesArray){
                if(l.contains(e.getX(), e.getY())){
                    pane.getChildren().remove(l);
                    graphModel.removeEdge(l.getInitialVertex(), l.getFinalVertex());
                    vertexArray.get(l.getInitialVertex()).getConnectedEdges().remove(l);//teste
                    vertexArray.get(l.getFinalVertex()).getConnectedEdges().remove(l);//teste
                    deleted.add(l);//teste
                }
            }
            edgesArray.removeAll(deleted);//teste
        };

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle().getUserData() == "Del") {
                pane.setCursor(Cursor.CROSSHAIR);
                pane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }else{
                pane.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        });
    }

    //adiciona funcionalidade de criar a popup
    public void openConnectVertexScreen() {
        view.getAddE().setOnAction(e ->{
            ConnectVertexController popupController = new ConnectVertexController(this.primaryStage, this);
        });
    }
    public void openRandomGraphScreen() {
        view.getRandom().setOnAction(e ->{
           RandomGraphController popupRandom = new RandomGraphController(this.primaryStage, this);
        });
    }

    //cria aresta ligando dois nós
    public void callDrawEdgeOnView(int initialVertexIndex, int finalVertexIndex) {
        graphModel.connectVertexes(initialVertexIndex, finalVertexIndex);
        view.drawEdge(initialVertexIndex, finalVertexIndex);
    }

    // novas
    private void executeDFS() {
        DepthFirstSearch dfs = new DepthFirstSearch(this.graphModel);
        this.searchExecution = dfs.getSearchExecution();
    }

    // novas
    private boolean goToNextExecutionStep() {
        currentState += 1;
        if (currentState >= searchExecution.size()) {
            return false;
        }
        view.setCurrentSearchState(searchExecution.get(currentState));
        view.reloadGraphState();
        return true;
    }

    // novas
    private boolean goToPreviousExecutionStep() {
        currentState -= 1;
        if (currentState < 0) {
            return false;
        }
        view.setCurrentSearchState(searchExecution.get(currentState));
        view.reloadGraphState();
        return true;
    }
}

