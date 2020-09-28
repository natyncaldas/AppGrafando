package grafando.Model;

public interface GraphInterface{
    public void addVertex(Integer... v);
    public void connectVertexes(int v1, int v2);
    public void removeEdge(int v1, int v2);
    public void removeVertex(int v);
    public int totalVertexes();
}