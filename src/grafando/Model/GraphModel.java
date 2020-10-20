package grafando.Model;

import java.io.*;
import java.util.*;

public class GraphModel {
    private final VertexModel vertexes;
    private final TreeMap<Integer, VertexModel> adjList;

    // Um SINGLETON que é usado para se referenciar em todas as telas
    // que necessitam de referência específica ao grafo.
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

    // Testa se existe uma aresta conectando dois vértices
    public boolean hasEdge(int v1, int v2){
        return adjList.get(v1).getVertexSet().contains(v2) || adjList.get(v2).getVertexSet().contains(v1);
    }

    // Testa se existe o vertice especificado
    public boolean hasVertex(int v){
        return vertexes.getVertexSet().contains(v);
    }

    // Adiciona o vertice na lista de vertices e o insere no grafo
    public void addVertex(Integer... v){
        for(Integer c: v){
            vertexes.getVertexSet().add(c);
            adjList.put(c, new VertexModel());
        }
    }

    // Conecta dois vertices, checando se eles já não estão conectados antes disso
    public void connectVertexes(int v1, int v2){
        if((hasVertex(v1) && hasVertex(v2)) && !hasEdge(v1,v2)){
            adjList.get(v1).getVertexSet().add(v2);
            adjList.get(v2).getVertexSet().add(v1);
        }
    }

    // Checa se existe um vertice, e então o remove, além de remover suas arestas
    public void removeVertex(int v){
        if(hasVertex(v)) {
            vertexes.getVertexSet().remove(v);
            for (Integer c : adjList.keySet()) {
                removeEdge(c, v);
            }
            adjList.remove(v);
        }
    }

    // Remove as arestas relacionadas a um vertice
    public void removeEdge(int v1, int v2){
        if(hasEdge(v1,v2)){
            adjList.get(v1).getVertexSet().remove(v2);
            adjList.get(v2).getVertexSet().remove(v1);
        }
    }

    public void clearGraph(){
        adjList.clear();
        vertexes.getVertexSet().clear();
    }

    public static int graphSize(GraphModel graphModel){
        return graphModel.vertexes.getVertexSet().size();
    }

    /*
    Gera-se um grafo aleatório:
    Primeiro o estado atual é limpo;
    Então de acordo com o número de vertices requerido, ele é criado
    Na hora de se conectar os vertices é feito uma escolha aleatoria de quais devem se conectar
     */
    public static void generateRandomGraph(int numberVertexes, GraphModel randomGraph){
        randomGraph.clearGraph();
        for (int i = 0; i < numberVertexes; i++){
            randomGraph.addVertex(i);
        }
        Random random = new Random();
        int numberEdges = random.nextInt((graphSize(randomGraph)/2)+10);
        for (int i=0; i < numberEdges; i++){
            int vertexA = random.nextInt(graphSize(randomGraph));
            int vertexB = random.nextInt(graphSize(randomGraph));
            if(!(randomGraph.hasEdge(vertexA, vertexB)) && vertexA != vertexB){
                randomGraph.connectVertexes(vertexA, vertexB);
            }
        }
    }

    /*
    Um arquivo com posiçoes para um grafo de 25 vertices é disponibilizado junto
    do programa. Ele serve para que o grafo aleatorio seja minimamente legivel,
    no caso de um grafo com menos de 25 vertices, uma amostra aleatoria desses vertices
    é recolhida e é criado na tela a partir dela o grafo na tela.
     */
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

    /*
    Abre um arquivo criado previamente pelo programa, carregando o grafo na tela
    O grafo possuirá as mesmas conexoes e numero de vertices, apesar de ter
    a chance de suas posiçoes mudarem.
    É feito um teste de chave para se checar se o arquivo é valido.
     */
    public void open(File file) {;
        try {
            InputStream input = new FileInputStream(file);
            byte[] stream = input.readAllBytes();

            if(this.hasSignature(stream)){
                this.clearGraph();
                int i = 6;
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
        } catch (ArithmeticException | IOException e) {
            throw new ArithmeticException();
        }
    }

    /*
    Salva um grafo em formato binário em um arquivo
    É escrito um 'cabeçalho' no arquivo, para se identificar (signature)
    que é um arquivo válido e gerado pelo programa.
     */
    public void save(File file){

        file = new File(file.getAbsolutePath());
        try {
            file.createNewFile();
            OutputStream output = new FileOutputStream(file);

            output.write('G');
            output.write('R');
            output.write('A');
            output.write('P');
            output.write('H');
            output.write(0x7);

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

    // Testa se a assinatura do arquivo de grafo salvo é a criada pelo programa
    private boolean hasSignature(byte[] stream){
        return (stream[0] == 'G' && stream[1] == 'R' && stream[2] == 'A' && stream[3] == 'P' && stream[4] == 'H' && stream[5] == 0x7);
    }
}
