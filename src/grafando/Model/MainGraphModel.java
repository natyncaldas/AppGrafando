package grafando.Model;

import java.util.TreeMap;

public class MainGraphModel {
    private final MainVertexModel vertices;
    private final TreeMap<Integer,MainVertexModel> lista_adj;

    private static MainGraphModel sharedModelGraph = new MainGraphModel();

    private MainGraphModel(){
        vertices = new MainVertexModel();
        lista_adj = new TreeMap<>();
    }

    public static MainGraphModel getInstance() {
        return sharedModelGraph;
    }

    public MainVertexModel getVertices(){
        return vertices;
    }

    public TreeMap<Integer,MainVertexModel> getLista(){
        return lista_adj;
    }

    private boolean existeAresta(int v1, int v2){
        return lista_adj.get(v1).getConjunto().contains(v2);
    }

    public boolean existeVertice(int v){
        return vertices.getConjunto().contains(v);
    }

    public void adicionarVertice(Integer... v){
        for(Integer c: v){
            vertices.getConjunto().add(c);
            lista_adj.put(c, new MainVertexModel());
        }
    }
    public void conectarVertices(int v1, int v2){
        if((existeVertice(v1) && existeVertice(v2)) && !existeAresta(v1,v2)){
            lista_adj.get(v1).getConjunto().add(v2);
            lista_adj.get(v2).getConjunto().add(v1);
        }
        //else: joga uma exceção
    }

    public void removerVertice(int v){
        if(existeVertice(v)) {
            vertices.getConjunto().remove(v);
            for (Integer c : lista_adj.keySet()) {
                removerAresta(c, v);
            }
            lista_adj.remove(v);
        }
        //else: joga uma exceção
    }

    public void removerAresta(int v1, int v2){
        if(existeAresta(v1,v2)){
            lista_adj.get(v1).getConjunto().remove(v2);
            lista_adj.get(v2).getConjunto().remove(v1);
        }
        //else: joga uma exceção
    }

    public void limparGrafo(){
        lista_adj.clear();
        vertices.getConjunto().clear();
    }

    public int totalVertices(){
        return vertices.getConjunto().size();
    }

}