package grafando.View;

import grafando.Model.DepthFirstSearch;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainScreenView {
    //Declaração dos Nodes(elementos) da view
    private BorderPane root, commands;
    private GridPane editGraph, run;
    private Pane drawGraph;
    private VBox forward, backwards;
    private Button clear, addE, stopDFS, runDFS, random, previous, next;
    private RadioButton addV, delete;
    private ToggleGroup toggleAddDel;
    private MenuItem exit;
    private MenuItem save;
    private MenuItem open;
    private MenuItem screenshot;
    private ArrayList<Vertex> vertexes;
    private ArrayList<Edge> edges;
    private DepthFirstSearch currentSearchState;

    public MainScreenView() throws FileNotFoundException {
        this.setUpElements();
        this.setUpMenu();
        this.positionElements();
    }

    //Instanciação dos Nodes
    public void setUpElements() throws FileNotFoundException {
        this.setRootStyled(new BorderPane());
        this.setForwardStyled(new VBox());
        this.setBackwardsStyled(new VBox());
        this.setCommandsStyled(new BorderPane());
        this.setEditGraphStyled(new GridPane());
        this.setRunStyled(new GridPane());
        this.setDrawGraph(new Pane());
        this.setStopDFSStyled(new Button());
        this.setRunDFSStyled(new Button());
        this.setRandomStyled(new Button());
        this.setClearStyled(new Button("Clear"));
        this.setAddEStyled(new Button("Connect"));
        this.setNextStyled(new Button());
        this.setPreviousStyled(new Button());
        this.setAddVStyled(new RadioButton("Add Vertex"));
        this.setDeleteStyled(new RadioButton("Delete"));
        this.setToggleAddDel(new ToggleGroup());
        this.setExit(new MenuItem("Exit"));
        this.setSave(new MenuItem("Save"));
        this.setOpen(new MenuItem("Open"));
        this.setScreenshot(new MenuItem("Screenshot"));
        this.setVertexes(new ArrayList<>());
        this.setEdges(new ArrayList<>());
    }

    public void setUpMenu(){
        MenuBar menubar = new MenuBar();
        menubar.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
        Menu fileMenu = new Menu("File");
        Menu viewMenu = new Menu("View");
        fileMenu.getItems().addAll(this.save, this.open, this.exit);
        viewMenu.getItems().add(this.screenshot);
        menubar.getMenus().addAll(fileMenu, viewMenu);
        root.setTop(menubar);
    }

    //Posicionamento dos Nodes na interface gráfica
    public void positionElements(){
        this.root.setBottom(this.commands);
        this.root.setCenter(this.drawGraph);
        this.root.setRight(this.forward);
        this.root.setLeft(this.backwards);
        this.forward.getChildren().add(this.next);
        this.backwards.getChildren().add(this.previous);
        this.addV.setToggleGroup(this.toggleAddDel);
        this.delete.setToggleGroup(this.toggleAddDel);
        this.commands.setLeft(this.editGraph);
        this.commands.setRight(this.run);
        this.editGraph.addColumn(0, this.addV, this.delete);
        this.editGraph.addColumn(1, this.addE, this.clear);
        this.run.addColumn(0, this.random);
        this.run.addColumn(1, this.runDFS);
        this.run.addColumn(2, this.stopDFS);
    }

    //Métodos Setters e Getters para cada Node
    //(alguns Setters possuem o nome "Styled", para indicar que já atribuem a estilização do Node específico)
    public BorderPane getRoot() {
        return root;
    }

    private void setRootStyled(BorderPane root) {
        root.setBackground(new Background(new BackgroundFill(Color.web("#15202b"), CornerRadii.EMPTY, Insets.EMPTY)));
        this.root = root;
    }

    public VBox getForward() {
        return forward;
    }

    private void setForwardStyled(VBox forward) {
        forward.setAlignment(Pos.CENTER);
        this.forward = forward;
    }

    public VBox getBackwards() {
        return backwards;
    }

    private void setBackwardsStyled(VBox backwards) {
        backwards.setAlignment(Pos.CENTER);
        this.backwards = backwards;
    }

    public BorderPane getCommands() {
        return commands;
    }

    private void setCommandsStyled(BorderPane commands) {
        commands.setPadding(new Insets(7));
        commands.setBorder(new Border(new BorderStroke(Color.DARKSLATEGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.commands = commands;
    }
    public GridPane getEditGraph() {
        return editGraph;
    }

    private void setEditGraphStyled(GridPane editGraph) {
        editGraph.setAlignment(Pos.CENTER_LEFT);
        editGraph.setVgap(5);
        editGraph.setHgap(15);
        this.editGraph = editGraph;
    }

    public GridPane getRun() {
        return run;
    }

    private void setRunStyled(GridPane run) {
        run.setAlignment(Pos.CENTER_RIGHT);
        run.setHgap(15);
        this.run = run;
    }
    public Pane getDrawGraph() {
        return drawGraph;
    }

    private void setDrawGraph(Pane drawGraph) {
        this.drawGraph = drawGraph;
    }

    public Button getStopDFS() {
        return stopDFS;
    }
    private void setStopDFSStyled(Button stopDFS) throws FileNotFoundException {
        FileInputStream input=new FileInputStream("resources/icons/stop.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        stopDFS.setGraphic(img);
        stopDFS.setBackground(null);
        stopDFS.setTooltip(new Tooltip("Stop depth-first search"));
        colorButtonOnMouseEntered(stopDFS);
        this.stopDFS = stopDFS;
    }

    public Button getRunDFS() {
        return runDFS;
    }

    private void setRunDFSStyled(Button runDFS) throws FileNotFoundException {
        FileInputStream input=new FileInputStream("resources/icons/play.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        runDFS.setGraphic(img);
        runDFS.setBackground(null);
        runDFS.setTooltip(new Tooltip("Run depth-first search"));
        colorButtonOnMouseEntered(runDFS);
        this.runDFS = runDFS;
    }

    public Button getRandom() {
        return random;
    }

    private void setRandomStyled(Button random) throws FileNotFoundException {
        FileInputStream input=new FileInputStream("resources/icons/dice.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        random.setGraphic(img);
        random.setBackground(null);
        random.setTooltip(new Tooltip("Generate random graph"));
        colorButtonOnMouseEntered(random);
        this.random = random;
    }
    public Button getClear() {
        return clear;
    }

    private void setClearStyled(Button clear) {
        clear.setTextFill(Color.DARKSLATEGRAY);
        clear.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        clear.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(50), Insets.EMPTY)));
        colorButtonOnMouseEntered(clear);
        this.clear = clear;
    }

    public Button getAddE() {
        return addE;
    }

    private void setAddEStyled(Button addE) {
        addE.setTextFill(Color.DARKSLATEGRAY);
        addE.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        addE.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(50), Insets.EMPTY)));
        colorButtonOnMouseEntered(addE);
        this.addE = addE;
    }

    public Button getNext(){
        return next;
    }

    private void setNextStyled(Button next) throws FileNotFoundException {
        FileInputStream input=new FileInputStream("resources/icons/right_arrow.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        next.setGraphic(img);
        next.setBackground(null);
        next.setTooltip(new Tooltip("Next DFS state"));
        next.setOpacity(0);
        next.setDisable(true);
        colorButtonOnMouseEntered(next);
        this.next = next;
    }

    public Button getPrevious(){
        return previous;
    }

    private void setPreviousStyled(Button previous) throws FileNotFoundException {
        FileInputStream input=new FileInputStream("resources/icons/left_arrow.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        previous.setGraphic(img);
        previous.setBackground(null);
        previous.setTooltip(new Tooltip("Previous DFS state"));
        previous.setOpacity(0);
        previous.setDisable(true);
        colorButtonOnMouseEntered(previous);
        this.previous = previous;
    }

    public RadioButton getAddV() {
        return addV;
    }

    private void setAddVStyled(RadioButton addV) {
        addV.setTextFill(Color.LIGHTGRAY);
        addV.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        addV.setUserData("AddV");
        this.addV = addV;
    }

    public RadioButton getDelete() {
        return delete;
    }

    private void setDeleteStyled(RadioButton delete) {
        delete.setTextFill(Color.LIGHTGRAY);
        delete.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        delete.setUserData("Del");
        this.delete = delete;
    }

    public ToggleGroup getToggleAddDel() {
        return toggleAddDel;
    }

    private void setToggleAddDel(ToggleGroup toggleAddDel) {
        this.toggleAddDel = toggleAddDel;
    }

    public MenuItem getExit() {
        return exit;
    }

    public void setExit(MenuItem exit) {
        this.exit = exit;
    }

    public MenuItem getSave() {
        return save;
    }

    public void setSave(MenuItem save) {
        this.save = save;
    }

    public MenuItem getOpen() {
        return open;
    }

    public void setOpen(MenuItem open) {
        this.open = open;
    }

    public MenuItem getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(MenuItem screenshot) {
        this.screenshot = screenshot;
    }

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    private void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<Edge> getEdges() { return edges; }

    public void setEdges(ArrayList<Edge> edges){
        this.edges = edges;
    }

    public void drawVertex(double x, double y){
        Vertex vertex = new Vertex();

        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        MainScreenView.styleVertexShape(circle, Color.SPRINGGREEN, Color.SPRINGGREEN);

        Text txt = new Text(""+vertexes.size());
        MainScreenView.styleVertexText(txt, Color.SPRINGGREEN);

        vertex.setVertex(new StackPane(), circle, txt);
        vertexes.add(vertex);
        vertex.getVertex().setLayoutX(x-13);
        vertex.getVertex().setLayoutY(y-13);

        drawGraph.getChildren().addAll(vertex.getVertex());
    }

    public void drawEdge(int initialVertexIndex, int finalVertexIndex) {

        Edge line = styleEdge(new Edge(initialVertexIndex, finalVertexIndex), Color.SPRINGGREEN);

        Circle c1 = this.vertexes.get(initialVertexIndex).getShape();
        Circle c2 = this.vertexes.get(finalVertexIndex).getShape();

        Point2D dir = new Point2D(c2.getCenterX() - c1.getCenterX(), c2.getCenterY() - c1.getCenterY()).normalize();
        Point2D off = dir.multiply(c1.getRadius());
        line.setStartX(c1.getCenterX() + off.getX());
        line.setStartY(c1.getCenterY() + off.getY());

        dir = dir.multiply(-1);
        off = dir.multiply(c2.getRadius());
        line.setEndX(c2.getCenterX() + off.getX());
        line.setEndY(c2.getCenterY() + off.getY());

        edges.add(line);
        drawGraph.getChildren().add(line);
        this.getVertexes().get(initialVertexIndex).connectEdge(line);
        this.getVertexes().get(finalVertexIndex).connectEdge(line);
    }

    //Métodos estáticos puramente para estilização
    private static void colorButtonOnMouseEntered(Button button){
        button.setOnMouseEntered(mouseEvent -> {
            final Color bg = button.getGraphic() == null ? Color.LIGHTGREEN : Color.DARKSLATEGRAY;
            button.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
        });
        button.setOnMouseExited(mouseEvent -> {
            final Color bg = button.getGraphic() == null ? Color.LIGHTGRAY : Color.TRANSPARENT;
            button.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
        });
    }

    public static void styleVertexShape(Circle vertexShape, Color color, Color shadow) {
        vertexShape.setRadius(13);
        vertexShape.setFill(Color.TRANSPARENT);
        vertexShape.setStrokeType(StrokeType.CENTERED);
        vertexShape.setStrokeWidth(2);
        vertexShape.setStroke(color);
        DropShadow s = new DropShadow();
        s.setColor(shadow);
        s.setRadius(13);
        s.setSpread(0.00001);
        vertexShape.setEffect(s);
    }

    public static void styleVertexText(Text vertexText, Color color) {
        vertexText.setFill(color);
        vertexText.setFont(Font.loadFont("file:resources/fonts/OpenSans-SemiBold.ttf", 12));
    }

    private static Edge styleEdge(Edge line, Color color) {
        line.setStroke(color);
        line.setStrokeWidth(2);

        DropShadow s = new DropShadow();
        s.setColor(color);
        s.setRadius(13);
        s.setSpread(0.0001);
        line.setEffect(s);
        return line;
    }

    public void setDefaultEdgeAndVertexColors() {
        for (Vertex v: vertexes) {
            styleVertexShape(v.getShape(), Color.SPRINGGREEN, Color.SPRINGGREEN);
            styleVertexText(v.getIndex(), Color.SPRINGGREEN);
        }
        for (Edge e: edges) {
            styleEdge(e, Color.SPRINGGREEN);
        }
    }

    //nova
    public void setCurrentSearchState(DepthFirstSearch state) {
        this.currentSearchState = state;
    }

    // nova
    public void reloadGraphState() {
        // para cada vertice in vertexes
        // pega o index dele e vê a cor dele no dfs
        // colore
        for (Vertex v: vertexes) {
            String color = currentSearchState.getVertexColor(vertexes.lastIndexOf(v));
            if (color != null) {
                if (color.equals("white")) {
                    styleVertexShape(v.getShape(), Color.WHITE, Color.WHITE);
                    styleVertexText(v.getIndex(), Color.WHITE);
                }
                if (color.equals("gray")) {
                    styleVertexShape(v.getShape(), Color.LIGHTSLATEGRAY, Color.LIGHTSLATEGRAY);
                    styleVertexText(v.getIndex(), Color.LIGHTSLATEGRAY);
                }
                if (color.equals("black")) {
                    styleVertexShape(v.getShape(), Color.BLACK, Color.WHITE);
                    styleVertexText(v.getIndex(), Color.WHITE);
                }
            }else{
                MainScreenView.styleVertexShape(v.getShape(), Color.SPRINGGREEN, Color.SPRINGGREEN);
                MainScreenView.styleVertexText(v.getIndex(), Color.SPRINGGREEN);
            }
            try{
                int current = vertexes.lastIndexOf(v);
                int parent = currentSearchState.getVertexParent(current);

                for (Edge e:edges) {
                    if(parent != -1){
                        if(e.containsVertexPair(current, parent)){
                            e.setStroke(Color.WHITE);
                        }
                    }
                    assert color != null;
                    if(color.equals("white") && e.getStroke().equals(Color.WHITE) && v.getConnectedEdges().contains(e)){
                        MainScreenView.styleEdge(e, Color.SPRINGGREEN);
                    }
                }
            }catch (NullPointerException ignored){}
        }
    }

    public static void createGraphImageFile(Pane pane, File image) {
        try {
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage graphImage = pane.snapshot(parameters, null);
            ImageIO.write(SwingFXUtils.fromFXImage(graphImage, null), "png", image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

