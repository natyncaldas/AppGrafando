package grafando.View;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Vertex{
    private Circle shape;
    private Text index;
    private StackPane vertex;
    private boolean deleted;

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
        vertex.getChildren().addAll(this.shape, this.index);
        this.vertex = vertex;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
