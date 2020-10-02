package grafando.View;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainScreenView {
    //Declaração dos Nodes(elementos) da view
    private BorderPane root, commands;
    private GridPane editGraph, run;
    private Pane drawGraph;
    private VBox forward, backwards;
    private Button clear, addE, runDFS, random, previous, next;
    private RadioButton addV, delete;
    private ToggleGroup toggleAddDel;
    private ArrayList<Vertex> vertexes;
    private ArrayList<Edge> edges;

    public MainScreenView() throws FileNotFoundException {
        this.setUpElements();
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
        this.setRunDFSStyled(new Button());
        this.setRandomStyled(new Button());
        this.setClearStyled(new Button("Clear"));
        this.setAddEStyled(new Button("Connect"));
        this.setNextStyled(new Button());
        this.setPreviousStyled(new Button());
        this.setAddVStyled(new RadioButton("Add Vertex"));
        this.setDeleteStyled(new RadioButton("Delete"));
        this.setToggleAddDel(new ToggleGroup());
        this.setVertexes(new ArrayList<>());
        this.edges = new ArrayList<>();
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
        this.run.addColumn(0, this.runDFS);
        this.run.addColumn(1, this.random);
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
        FileInputStream input=new FileInputStream("resources/icons/arrow-31-24.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        next.setGraphic(img);
        next.setBackground(null);
        next.setTooltip(new Tooltip("Next DFS state"));
        colorButtonOnMouseEntered(next);
        this.next = next;
    }

    public Button getPrevious(){
        return previous;
    }

    private void setPreviousStyled(Button previous) throws FileNotFoundException {
        FileInputStream input=new FileInputStream("resources/icons/arrow-96-24.png");
        Image image = new Image(input);
        ImageView img=new ImageView(image);
        previous.setGraphic(img);
        previous.setBackground(null);
        previous.setTooltip(new Tooltip("Previous DFS state"));
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

    public ArrayList<Vertex> getVertexes() {
        return vertexes;
    }

    private void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<Edge> getEdges() { return edges; }

    public void drawEdge(int initialVertexIndex, int finalVertexIndex) {

        Edge line = styleEdge(new Edge(initialVertexIndex, finalVertexIndex));

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

    public static void styleVertexShape(Circle vertexShape) {
        vertexShape.setRadius(13);
        vertexShape.setFill(Color.TRANSPARENT);
        vertexShape.setStrokeType(StrokeType.CENTERED);
        vertexShape.setStrokeWidth(2);
        vertexShape.setStroke(Color.SPRINGGREEN);
        DropShadow s = new DropShadow();
        s.setColor(Color.SPRINGGREEN);
        s.setRadius(13);
        s.setSpread(0.00001);
        vertexShape.setEffect(s);
    }

    public static void styleVertexText(Text vertexText) {
        vertexText.setFill(Color.SPRINGGREEN);
        vertexText.setFont(Font.loadFont("file:resources/fonts/OpenSans-SemiBold.ttf", 12));
    }

    private static Edge styleEdge(Edge line) {
        line.setStroke(Color.SPRINGGREEN);
        line.setStrokeWidth(2);

        DropShadow s = new DropShadow();
        s.setColor(Color.SPRINGGREEN);
        s.setRadius(13);
        s.setSpread(0.0001);
        line.setEffect(s);
        return line;
    }
}

