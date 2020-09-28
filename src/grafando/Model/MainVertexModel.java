package grafando.Model;

import java.util.HashSet;

public class MainVertexModel {
    private HashSet<Integer> vertexSet;

    public MainVertexModel(){
        this.setVertexSet(new HashSet<>());
    }

    void setVertexSet(HashSet<Integer> vertexSet){
        this.vertexSet = vertexSet;
    }

    public HashSet<Integer> getVertexSet() {
        return this.vertexSet;
    }


}
