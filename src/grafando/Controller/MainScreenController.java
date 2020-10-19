package grafando.Controller;

import grafando.Model.DepthFirstSearch;
import grafando.Model.GraphModel;
import grafando.View.Edge;
import grafando.View.MainScreenView;
import grafando.View.Vertex;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        this.graphModel = GraphModel.getInstance();

        //Chamada de métodos com eventos para Nodes específicos
        this.colorPressedButton(this.view.getRunDFS(), this.view.getRandom(), this.view.getAddE(), this.view.getClear(), this.view.getNext(), this.view.getPrevious());
        this.drawVertex(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes());
        this.clearGraph(this.view.getDrawGraph(), this.view.getClear(), this.view.getVertexes());
        this.showDFSState(this.view.getNext(), this.view.getPrevious(), this.view.getRunDFS(), this.view.getStopDFS());
        this.deleteElements(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes(), this.view.getEdges());
        this.setupSaveGraphButton();
        this.setupOpenGraphButton();
        this.setupExitButton();

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

            if(pane.contains(e.getX() + 30, e.getY() + 30) && pane.contains(e.getX() - 30, e.getY() - 30)
                    && !(e.getTarget() instanceof StackPane) && !(e.getTarget() instanceof Circle)
                    && !(e.getTarget() instanceof Text)){

                graphModel.addVertex(vertexArray.size());
                this.view.drawVertex(e.getX(), e.getY());
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

    private void disableAllOnDFS(boolean b){
        this.view.getAddV().setDisable(b);
        this.view.getDelete().setDisable(b);
        this.view.getAddE().setDisable(b);
        this.view.getClear().setDisable(b);
        this.view.getRunDFS().setDisable(b);
        this.view.getRandom().setDisable(b);
        this.view.getDrawGraph().setDisable(b);
        this.view.getOpen().setDisable(b);
        this.view.getSave().setDisable(b);
    }

    public void showDFSState(Button b1, Button b2, Button run, Button stop){
        EventHandler<MouseEvent> eventHandler = e ->{
            if(e.getSource().equals(run)){
                b1.setOpacity(1);
                b2.setOpacity(1);
                b1.setDisable(false);
                currentState = -1;
                disableAllOnDFS(true);
                executeDFS();
            }
            if (e.getSource().equals(stop)) {
                run.setDisable(false);
                b1.setOpacity(0);
                b2.setOpacity(0);
                b1.setDisable(true);
                b2.setDisable(true);
                disableAllOnDFS(false);
                view.setDefaultEdgeAndVertexColors();
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

    private void callDrawGraph(Pane pane, ArrayList<Vertex> vertexArray, ArrayList<Edge> edgesArray) throws FileNotFoundException {

        pane.getChildren().clear();
        vertexArray.clear();
        edgesArray.clear();

        ArrayList<Double[]> positions = GraphModel.getPositionsArray();

        for (Integer i :graphModel.getVertexes().getVertexSet()) {
            this.view.drawVertex(positions.get(i)[0], positions.get(i)[1]);
        }
        for (Integer i :graphModel.getVertexes().getVertexSet()) {
            for (Integer j : graphModel.getAdjList().get(i).getVertexSet()) {
                this.callDrawEdgeOnView(i, j);
            }
        }
    }

    public void confirmRandomGraph(int graphSize) throws FileNotFoundException {
        GraphModel.generateRandomGraph(graphSize, graphModel);
        callDrawGraph(this.view.getDrawGraph(), this.view.getVertexes(), this.view.getEdges());
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

    private void setupSaveGraphButton() {
        this.view.getSave().setOnAction(mouseEvent -> {
            if (GraphModel.totalVertexes(this.graphModel) <= 25) {
                final FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Binary Graph File (.bin)", "*.bin");
                fileChooser.getExtensionFilters().add(filter);
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    this.graphModel.save(file);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("Graph too big to save.");
                alert.show();
            }
        });
    }

    private void setupOpenGraphButton() {
        this.view.getOpen().setOnAction(mouseEvent -> {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    this.graphModel.open(file);
                    this.callDrawGraph(this.view.getDrawGraph(), this.view.getVertexes(), this.view.getEdges());
                } catch (FileNotFoundException | ArithmeticException e) {
                    if (e instanceof ArithmeticException) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error!");
                        alert.setContentText("Invalid or corrupted file!");
                        alert.show();
                    }
                }
            }
        });
    }

    private void setupExitButton() {
        this.view.getExit().setOnAction(mouseEvent -> {
            primaryStage.close();
        });
    }
}

