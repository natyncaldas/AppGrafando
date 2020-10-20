package grafando.View;

import javafx.scene.shape.Line;

public class Edge extends Line {

    //****** Inicialização ******

    //Declaração dos atributos
    int initialVertex, finalVertex;

    //Construtor
    public Edge(int initialVertex, int finalVertex){
        this.initialVertex = initialVertex;
        this.finalVertex = finalVertex;
    }

    //****** Getters e Setters ******

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

    //****** Operações ******

    //Checa se a aresta contém um par de vértices
    public boolean containsVertexPair(int ini, int fin){
        return (ini == initialVertex && fin == finalVertex) || (ini == finalVertex && fin == initialVertex);
    }
}
