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

public class MainScreenController {
    //****** Inicialização ******

    //Declaração dos atributos utilizados pelo controller
    public Stage primaryStage;
    private MainScreenView view;
    public GraphModel graphModel;
    private ArrayList<DepthFirstSearch> searchExecution;
    private int currentState;

    //Construtor do controller
    public MainScreenController(Stage primaryStage) throws FileNotFoundException {

        this.setUpController(primaryStage);
        this.setUpButtonsActions();
        this.setUpPopUpScreens();
    }

    //Inicializa atributos
    private void setUpController(Stage primaryStage) throws FileNotFoundException {
        this.primaryStage = primaryStage; //~salva referência ao stage principal
        this.setView(new MainScreenView());
        this.graphModel = GraphModel.getInstance(); //~recebe singleton do model do grafo
    }

    //Chama métodos com eventos para Nodes específicos
    private void setUpButtonsActions(){

        this.colorPressedButton(this.view.getRunDFS(), this.view.getRandom(), this.view.getAddE(), this.view.getClear(), this.view.getNext(), this.view.getPrevious());
        this.callDrawVertexOnView(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes());
        this.clearGraph(this.view.getDrawGraph(), this.view.getClear(), this.view.getVertexes());
        this.showDFSState(this.view.getNext(), this.view.getPrevious(), this.view.getRunDFS(), this.view.getStopDFS());
        this.deleteElements(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes(), this.view.getEdges());
        this.setupSaveGraphButton();
        this.setupOpenGraphButton();
        this.setupExitButton();
        this.setupScreenshotButton();
    }

    //Chama métodos para abrir as telas Pop Up
    private void setUpPopUpScreens(){
        this.openConnectVertexScreen();
        this.openRandomGraphScreen();
    }

    //Getter e Setter para View
    public MainScreenView getView() {
        return view;
    }

    private void setView(MainScreenView view) {
        this.view = view;
    }

    //****** Métodos para adcionar eventos em Nodes ******

    //Colore botões ao serem clicados
    private void colorPressedButton(Button... b){
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

    //Desenha vértices no Pane DrawGraph, caso o ToggleButton "Add Vertex" estiver selecionado
    private void callDrawVertexOnView(Pane pane, ToggleGroup group, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e -> {

            if(pane.contains(e.getX() + 30, e.getY() + 30) && pane.contains(e.getX() - 30, e.getY() - 30)
                    && !(e.getTarget() instanceof StackPane) && !(e.getTarget() instanceof Circle)
                    && !(e.getTarget() instanceof Text)){

                graphModel.addVertex(vertexArray.size());
                this.view.drawVertex(e.getX(), e.getY()); //~chama o método na view
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

    //Desabilita ou abilita botões; método chamado durante a execução do DFS
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

    //Adiciona eventos em botões utilizados na execução do DFS; desabilita/abilita alguns botões
    private void showDFSState(Button b1, Button b2, Button run, Button stop){
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

    //Limpa o grafo na View e no Model caso o botão "Clear" seja clicado
    private void clearGraph(Pane pane, Button b, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e ->{
            pane.getChildren().clear();
            vertexArray.clear();
            graphModel.clearGraph();
        };
        b.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    //Desenha vértices e arestas de um Model já pronto
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

    //Gera grafo aleatório e chama método "callDrawGraph" para o desenhar
    public void confirmRandomGraph(int graphSize) throws FileNotFoundException {
        GraphModel.generateRandomGraph(graphSize, graphModel);
        callDrawGraph(this.view.getDrawGraph(), this.view.getVertexes(), this.view.getEdges());
    }

    //Deleta vértices ou arestas selecionadas pelo o usuário, caso o ToggleButton "Delete" estiver selecionado
    private void deleteElements(Pane pane, ToggleGroup group, ArrayList<Vertex> vertexArray, ArrayList<Edge> edgesArray){
        EventHandler<MouseEvent> eventHandler = e ->{

            ArrayList<Edge>deleted = new ArrayList<>();

            for (Vertex v:vertexArray) { //~deleta vértices e arestas ligadas neste
                if(v.getVertex().getChildren().contains(e.getTarget())) {

                    for (Edge l:v.getConnectedEdges()) {
                        graphModel.removeEdge(l.getInitialVertex(), l.getFinalVertex());
                        deleted.add(l);

                    }
                    for (Edge l:deleted){
                        int index  = vertexArray.indexOf(v) == l.getInitialVertex() ? l.getFinalVertex() : l.getInitialVertex();
                        vertexArray.get(index).getConnectedEdges().remove(l);
                    }

                    graphModel.removeVertex(vertexArray.indexOf(v));
                    pane.getChildren().removeAll(v.getConnectedEdges());
                    edgesArray.removeAll(v.getConnectedEdges());
                    v.getVertex().getChildren().removeAll();
                    pane.getChildren().remove(v.getVertex());
                    v.delete();
                    break;
                }
            }
            deleted = new ArrayList<>();

            for (Edge l: edgesArray){//~deleta arestas
                if(l.contains(e.getX(), e.getY())){
                    pane.getChildren().remove(l);
                    graphModel.removeEdge(l.getInitialVertex(), l.getFinalVertex());
                    vertexArray.get(l.getInitialVertex()).getConnectedEdges().remove(l);
                    vertexArray.get(l.getFinalVertex()).getConnectedEdges().remove(l);
                    deleted.add(l);
                }
            }
            edgesArray.removeAll(deleted);
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

    //Cria aresta conectando dois vértices
    public void callDrawEdgeOnView(int initialVertexIndex, int finalVertexIndex) {
        graphModel.connectVertexes(initialVertexIndex, finalVertexIndex);
        view.drawEdge(initialVertexIndex, finalVertexIndex);//~chama método de desenhar na View
    }

    //Inicia execução da busca
    private void executeDFS() {
        DepthFirstSearch dfs = new DepthFirstSearch(this.graphModel);
        this.searchExecution = dfs.getSearchExecution();
    }

    //Avança 1x no estado da busca
    private boolean goToNextExecutionStep() {
        currentState += 1;
        if (currentState >= searchExecution.size()) {
            return false;
        }
        view.setCurrentSearchState(searchExecution.get(currentState));
        view.reloadGraphState();
        return true;
    }

    //Volta 1x no estado da busca
    private boolean goToPreviousExecutionStep() {
        currentState -= 1;
        if (currentState < 0) {
            return false;
        }
        view.setCurrentSearchState(searchExecution.get(currentState));
        view.reloadGraphState();
        return true;
    }

    //Salva o grafo no arquivo escolhido pelo usuário caso o MenuItem "Save" seja selecionado
    private void setupSaveGraphButton() {
        this.view.getSave().setOnAction(mouseEvent -> {
            if (GraphModel.graphSize(this.graphModel) <= 25) {
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

    //Abre um arquivo de grafo escolhido pelo usuário caso o MenuItem "Open" seja selecionado
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

    //Fecha a aplicação caso o MenuItem "Exit" seja selecionado
    private void setupExitButton() {
        this.view.getExit().setOnAction(mouseEvent -> {
            primaryStage.close();
        });
    }

    //Salva uma imagem do grafo no arquivo escolhido pelo usuário caso o MenuItem "Screenshot" seja selecionado
    private void setupScreenshotButton() {
        this.view.getScreenshot().setOnAction(mouseEvent -> {

                final FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Graph Image File (.png)", "*.png");
                fileChooser.getExtensionFilters().add(filter);
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    MainScreenView.createGraphImageFile(this.view.getDrawGraph(), file);
                }
        });
    }

    //****** Métodos para criar os Popups ******

    //Cria Popup de conectar vértices
    public void openConnectVertexScreen() {
        view.getAddE().setOnAction(e ->{
            ConnectVertexController popupController = new ConnectVertexController(this.primaryStage, this);
        });
    }

    //Cria Popup de gerar grafo aleatório
    public void openRandomGraphScreen() {
        view.getRandom().setOnAction(e ->{
            RandomGraphController popupRandom = new RandomGraphController(this.primaryStage, this);
        });
    }
}



