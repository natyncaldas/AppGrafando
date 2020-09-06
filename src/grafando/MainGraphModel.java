package grafando;

import java.util.HashMap;

public class MainGraphModel {
    private final MainVertexModel vertices;
    private final HashMap<Integer,MainVertexModel> lista_adj;

    public MainGraphModel(){
        vertices = new MainVertexModel();
        lista_adj = new HashMap<>();
    }

    public MainVertexModel getVertices(){
        return vertices;
    }

    public HashMap<Integer,MainVertexModel> get_lista(){
        return lista_adj;
    }

    public boolean existeAresta(int v1, int v2){
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
        if(!existeAresta(v1,v2) && (existeVertice(v1) && existeVertice(v2))){
            lista_adj.get(v1).getConjunto().add(v2);
            lista_adj.get(v1).getConjunto().add(v1);
        }
    }

    public void removerVertice(int v){
        if(existeVertice(v)){
            vertices.getConjunto().remove(v);
            lista_adj.remove(v);
        }
        for(Integer c: lista_adj.keySet()){
            if(existeAresta(v,c)){
                removerAresta(v,c);
            }
        }
    }

    public void removerAresta(int v1, int v2){
        if(existeAresta(v1,v2)) {
            lista_adj.get(v1).getConjunto().remove(v2);
            lista_adj.get(v2).getConjunto().remove(v1);
        }
    }

    public void limparGrafo(){
        lista_adj.clear();
        vertices.getConjunto().clear();
    }

    public int totalVertices(){
        return vertices.getConjunto().size();
    }

}
