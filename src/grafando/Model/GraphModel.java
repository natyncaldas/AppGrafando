package grafando.Model;

import java.io.*;
import java.util.*;

public class GraphModel {
    private final VertexModel vertexes;
    private final TreeMap<Integer, VertexModel> adjList;

    private static GraphModel sharedModelGraph = new GraphModel();

    private GraphModel(){
        vertexes = new VertexModel();
        adjList = new TreeMap<>();
    }

    public static GraphModel getInstance() {
        return sharedModelGraph;
    }

    public VertexModel getVertexes(){
        return vertexes;
    }

    public TreeMap<Integer, VertexModel> getAdjList(){
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
            adjList.put(c, new VertexModel());
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

    public static int totalVertexes(GraphModel graphModel){
        return graphModel.vertexes.getVertexSet().size();
    }

    public static void generateRandomGraph(int numberVertexes, GraphModel randomGraph){
        randomGraph.clearGraph();
        for (int i = 0; i < numberVertexes; i++){
            randomGraph.addVertex(i);
        }
        Random random = new Random();
        int numberEdges = random.nextInt((totalVertexes(randomGraph)/2)+10);
        for (int i=0; i < numberEdges; i++){
            int vertexA = random.nextInt(totalVertexes(randomGraph));
            int vertexB = random.nextInt(totalVertexes(randomGraph));
            if(!(randomGraph.existEdge(vertexA, vertexB)) && vertexA != vertexB){
                randomGraph.connectVertexes(vertexA, vertexB);
            }
        }
    }

    public static ArrayList<Double[]> getPositionsArray() throws FileNotFoundException {
        ArrayList<Double[]> pos = new ArrayList<>();
        File file = new File("resources/data_source/positions.txt");
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            Double[] coord = new Double[2];
            String[] line = scanner.nextLine().split("\\s");
            coord[0] = Double.parseDouble(line[0]);
            coord[1] = Double.parseDouble(line[1]);
            pos.add(coord);
        }

        Random random = new Random();
        for(int i = 0; i < random.nextInt(); i++){
            Collections.shuffle(pos);
       }
        return pos;
    }

    public void open(File file){;
        try {
            InputStream input = new FileInputStream(file);
            byte[] stream = input.readAllBytes();
            this.clearGraph();
            if(this.hasSignature(stream)){
                int i = 2;
                while (stream[i] != -1) {
                    int v = stream[i];
                    this.addVertex(v);

                    i++;
                }

                char ini = 0;
                for (i++; i < stream.length; i++) {
                    ini = (stream[i-1] == -1) ? (char)stream[i] : ini;
                    if(stream[i] != -1 && stream[i-1] != -1){
                        this.connectVertexes(ini, stream[i]);
                    }
                }

            }else{
                throw new ArithmeticException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(){
        File file = new File("saved_graph.bin");

        int i = 1;
        while (file.exists()){
            file = new File("saved_graph("+i+").bin");
            i++;
        }
        try {
            file.createNewFile();
            OutputStream output = new FileOutputStream(file);

            output.write('G');
            output.write('R');
            output.write('A');
            output.write('P');
            output.write('H');

            for (Integer v:this.vertexes.getVertexSet()) {
                output.write(v);
            }
            output.write(0xff);
            for (Integer v:this.adjList.keySet()) {
                output.write(v);
                for (Integer u:this.adjList.get(v).getVertexSet()) {
                    output.write(u);
                }
                output.write(0xff);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean hasSignature(byte[] stream){
        return (stream[0] == 'G' && stream[1] == 'R' && stream[2] == 'A' && stream[3] == 'P' && stream[4] == 'H');
    }
}
