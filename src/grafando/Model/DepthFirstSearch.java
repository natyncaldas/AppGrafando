package grafando.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class DepthFirstSearch extends
        GraphSearchAlgorithms{
    private GraphModel Graph;

    private HashMap<Integer, String> vertexesColors;
    private HashMap <Integer, Integer> vertexParent;
    private HashMap <Integer, Integer> vertexInitialTime, vertexFinalTime;
    private ArrayList<DepthFirstSearch> searchExecution;
    private int executionTime;

    public DepthFirstSearch(GraphModel G){
        this();
        setGraph(G);
        executeSearch();
    }

    private DepthFirstSearch(){

        searchExecution = new ArrayList<>();
        vertexFinalTime = new HashMap<>();
        vertexInitialTime = new HashMap<>();
        vertexParent = new HashMap<>();
        vertexesColors = new HashMap<>();

    }


    public void executeSearch(){
        this.executionTime = 0;


        for(Integer v: this.Graph.getVertexes().getVertexSet()){
            this.setInitialVertexTime(v, 0);
            this.setFinalVertexTime(v, 0);
            this.setVertexParent(v, -1);
            this.setVertexColor(v, "white");
        }

        for(Integer u: this.Graph.getVertexes().getVertexSet()){
            if(getVertexColor(u).equals("white")){
                this.visitVertex(u);
            }
        }
    }

    private void visitVertex(int u){
        copyAttributes(this.vertexesColors,this.vertexParent,this.vertexInitialTime,this.vertexFinalTime);

        setVertexColor(u, "gray");
        this.executionTime++;
        setInitialVertexTime(u, executionTime);

        copyAttributes(this.vertexesColors,this.vertexParent,this.vertexInitialTime,this.vertexFinalTime);


        for(Integer v: Graph.getAdjVertex(u)){
            if(getVertexColor(v).equals("white")){
                setVertexParent(v, u);
                visitVertex(v);
            }
        }

        setVertexColor(u, "black");
        this.executionTime++;
        setFinalVertexTime(u, executionTime);

        copyAttributes(this.vertexesColors,this.vertexParent,this.vertexInitialTime,this.vertexFinalTime);

    }


    private void copyAttributes(HashMap<Integer,String> color, HashMap<Integer,Integer> parent, HashMap<Integer,Integer> i, HashMap<Integer,Integer> f){

        DepthFirstSearch temp = new DepthFirstSearch();

        temp.Graph = this.Graph;
        temp.vertexInitialTime = new HashMap<>(i);
        temp.vertexFinalTime = new HashMap<>(f);
        temp.vertexParent = new HashMap<>(parent);
        temp.vertexesColors = new HashMap<>(color);

        this.searchExecution.add(temp);
    }

    private void setGraph(GraphModel G){
        this.Graph = G;
    }

    private void setInitialVertexTime(int v, int t){
        vertexInitialTime.put(v,t);
    }

    private void setFinalVertexTime(int v, int t){
        vertexFinalTime.put(v, t);
    }

    private void setVertexParent(int v, int p){
        vertexParent.put(v, p);
    }

    private void setVertexColor(int v, String c){
        vertexesColors.put(v, c);
    }

    public ArrayList<DepthFirstSearch> getSearchExecution(){
        return searchExecution;
    }

    public String getVertexColor(int v){
        return vertexesColors.get(v);
    }

    public int getVertexParent(int v){
        return vertexParent.get(v);
    }

    public int getInitialVertexTime(int v){
        return vertexInitialTime.get(v);
    }

    public int getFinalVertexTime(int v){
        return vertexFinalTime.get(v);
    }
    public GraphModel getGraph(){
        return Graph;
    }
}