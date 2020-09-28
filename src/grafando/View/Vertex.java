package grafando.View;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Vertex{
    private Circle shape;
    private Text index;
    private StackPane vertex;
    private boolean deleted;
    private ArrayList<Line> connectedEdges;

    public Vertex() {
        this.deleted = false;
    }

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

    public void connectEdge(Line edge) {
        connectedEdges.add(edge);
    }

    public ArrayList<Line> getConnectedEdges() {
        return connectedEdges;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
