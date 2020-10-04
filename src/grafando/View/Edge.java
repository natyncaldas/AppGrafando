package grafando.View;

import javafx.scene.shape.Line;

public class Edge extends Line {
    int initialVertex, finalVertex;

    public Edge(int initialVertex, int finalVertex){
        this.initialVertex = initialVertex;
        this.finalVertex = finalVertex;
    }

    public int getInitialVertex() {
        return initialVertex;
    }

    public void setInitialVertex(int initialVertex) {
        this.initialVertex = initialVertex;
    }

    public int getFinalVertex() {
        return finalVertex;
    }

    public void setFinalVertex(int finalVertex) {
        this.finalVertex = finalVertex;
    }

    public boolean containsVertexPair(int ini, int fin){
        return (ini == initialVertex && fin == finalVertex) || (ini == finalVertex && fin == initialVertex);
    }
}
