package grafando.Model;

import java.util.HashSet;

public class VertexModel {
    private HashSet<Integer> vertexSet;

    public VertexModel(){
        this.setVertexSet(new HashSet<>());
    }

    void setVertexSet(HashSet<Integer> vertexSet){
        this.vertexSet = vertexSet;
    }

    public HashSet<Integer> getVertexSet() {
        return this.vertexSet;
    }


}
