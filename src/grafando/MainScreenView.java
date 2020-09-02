package grafando;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private Button clear, addE, runDFS, random;
    private RadioButton addV, delete;
    private ToggleGroup toggleAddDel;
    private ArrayList<Integer> numbers;
    private ArrayList<StackPane> vertexes;

    public MainScreenView() throws FileNotFoundException {
        //Instanciação dos Nodes
        this.setRootStyled(new BorderPane());
        this.setCommandsStyled(new BorderPane());
        this.setEditGraphStyled(new GridPane());
        this.setRunStyled(new GridPane());
        this.setDrawGraph(new Pane());
        this.setRunDFSStyled(new Button());
        this.setRandomStyled(new Button());
        this.setClearStyled(new Button("Clear"));
        this.setAddEStyled(new Button("Connect"));
        this.setAddVStyled(new RadioButton("Add Vertex"));
        this.setDeleteStyled(new RadioButton("Delete"));
        this.setToggleAddDel(new ToggleGroup());
        this.setNumbers(new ArrayList<>());
        this.setVertexes(new ArrayList<>());
        //Posicionamento dos Nodes na interface gráfica
        this.root.setBottom(this.commands);
        this.root.setCenter(this.drawGraph);
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
        this.random = random;
    }
    public Button getClear() {
        return clear;
    }

    private void setClearStyled(Button clear) {
        clear.setTextFill(Color.DARKSLATEGRAY);
        clear.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        clear.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(50), Insets.EMPTY)));
        this.clear = clear;
    }

    public Button getAddE() {
        return addE;
    }

    private void setAddEStyled(Button addE) {
        addE.setTextFill(Color.DARKSLATEGRAY);
        addE.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        addE.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(50), Insets.EMPTY)));
        this.addE = addE;
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

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    private void setNumbers(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<StackPane> getVertexes() {
        return vertexes;
    }

    private void setVertexes(ArrayList<StackPane> vertexes) {
        this.vertexes = vertexes;
    }

    //Métodos estáticos puramente para estilização
    protected static void styleVertexShape(Circle vertexShape) {
        vertexShape.setRadius(13);
        vertexShape.setFill(Color.TRANSPARENT);
        vertexShape.setStrokeType(StrokeType.CENTERED);
        vertexShape.setStroke(Color.SPRINGGREEN);
        DropShadow s = new DropShadow();
        s.setColor(Color.SPRINGGREEN);
        s.setRadius(13);
        s.setSpread(0.0001);
        vertexShape.setEffect(s);
    }

    protected static void styleVertexText(Text vertexText) {
        vertexText.setFill(Color.SPRINGGREEN);
        vertexText.setFont(Font.loadFont("file:resources/fonts/OpenSans-SemiBold.ttf", 12));
    }
}

