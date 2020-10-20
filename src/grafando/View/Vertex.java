package grafando.View;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Vertex{

    //****** Inicialização ******

    //Declaração dos atributos
    private Circle shape;
    private Text index;
    private StackPane vertex;
    private boolean deleted;
    private ArrayList<Edge> connectedEdges;

    //Construtor
    public Vertex() {
        this.deleted = false;
    }

    //****** Getters e Setters ******

    public Circle getShape() {
        return shape;
    }

    public Text getIndex() {
        return index;
    }

    public StackPane getVertex() {
        return vertex;
    }

    public void setVertex(StackPane vertex, Circle shape, Text index) {
        this.shape = shape;
        this.index = index;
        this.connectedEdges = new ArrayList<>();
        vertex.getChildren().addAll(this.shape, this.index);
        this.vertex = vertex;
    }

    public void connectEdge(Edge edge) {
        connectedEdges.add(edge);
    }

    public ArrayList<Edge> getConnectedEdges() {
        return connectedEdges;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
