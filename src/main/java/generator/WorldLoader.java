package generator;

import utils.Pair;

import java.util.Collection;
import java.util.Map;

public class WorldLoader {

    private Generator generator;
    private Map<Pair<Integer, Integer>, Pair<Object, Object>> changes; //object is temporary and will be replaced be appropriate class

    public void loadChanges() {
        //from disk
    }

    public void saveChanges() {
        //to disk
    }

    public Map<Pair<Integer, Integer>, Pair<Object, Object>> loadChunk(int x, int y) { //object is temporary and will be replaced be appropriate class
        //generator.generateChunk(x,y);
        //apply changes
        return null;
    }

    public void saveChunk(int x, int y, Collection<Pair<Object, Object>> state) { //object is temporary and will be replaced be appropriate class
        //generator.generateChunk(x,y)
        //calculate differences between ^ and state
        //save differences
        return;
    }
}
