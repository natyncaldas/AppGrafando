package grafando.Model;

import java.util.HashSet;
import java.util.TreeMap;

public class MainGraphModel {
    private final MainVertexModel vertexes;
    private final TreeMap<Integer,MainVertexModel> adjList;

    private static MainGraphModel sharedModelGraph = new MainGraphModel();

    private MainGraphModel(){
        vertexes = new MainVertexModel();
        adjList = new TreeMap<>();
    }

    public static MainGraphModel getInstance() {
        return sharedModelGraph;
    }

    public MainVertexModel getVertexes(){
        return vertexes;
    }

    public TreeMap<Integer,MainVertexModel> getAdjList(){
        return adjList;
    }

    public HashSet<Integer> getAdjVertex(int v){
        return this.adjList.get(v).getVertexSet();
    }

    public boolean existEdge(int v1, int v2){
        return adjList.get(v1).getVertexSet().contains(v2) || adjList.get(v2).getVertexSet().contains(v1);
    }

    public boolean existVertex(int v){
        return vertexes.getVertexSet().contains(v);
    }

    public void addVertex(Integer... v){
        for(Integer c: v){
            vertexes.getVertexSet().add(c);
            adjList.put(c, new MainVertexModel());
        }
    }
    public void connectVertexes(int v1, int v2){
        if((existVertex(v1) && existVertex(v2)) && !existEdge(v1,v2)){
            adjList.get(v1).getVertexSet().add(v2);
            adjList.get(v2).getVertexSet().add(v1);
        }
        //else: joga uma exceção
    }

    public void removeVertex(int v){
        if(existVertex(v)) {
            vertexes.getVertexSet().remove(v);
            for (Integer c : adjList.keySet()) {
                removeEdge(c, v);
            }
            adjList.remove(v);
        }
        //else: joga uma exceção
    }

    public void removeEdge(int v1, int v2){
        if(existEdge(v1,v2)){
            adjList.get(v1).getVertexSet().remove(v2);
            adjList.get(v2).getVertexSet().remove(v1);
        }
        //else: joga uma exceção
    }

    public void clearGraph(){
        adjList.clear();
        vertexes.getVertexSet().clear();
    }

    public int totalVertexes(){
        return vertexes.getVertexSet().size();
    }

}
