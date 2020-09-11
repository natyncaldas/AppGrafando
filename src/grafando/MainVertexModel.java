package grafando;

import java.util.HashSet;

public class MainVertexModel {
    private HashSet<Integer> conjunto;

    public MainVertexModel(){
        this.setConjunto(new HashSet<>());
    }

    void setConjunto(HashSet<Integer> conjunto){
        this.conjunto = conjunto;
    }

    public HashSet<Integer> getConjunto() {
        return this.conjunto;
    }


}
