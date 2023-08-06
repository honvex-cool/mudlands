package generator;

import utils.Pair;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class WorldLoader {

    private Generator generator;
    private Map<Pair<Integer, Integer>, FieldStruct> changes;

    public WorldLoader(Generator generator) {
        this.generator = generator;
    }

    public void loadChanges() {
        //from disk
    }

    public void saveChanges() {
        //to disk
    }

    public Map<Pair<Integer, Integer>, FieldStruct> loadChunk(int chunk_x, int chunk_y) {
        //generator.generateChunk(x,y);
        //apply changes
        return null;
    }

    public void saveChunk(int chunk_x, int chunk_y, Map<Pair<Integer, Integer>, FieldStruct> diffs) {
        //generator.generateChunk(x,y)
        //calculate differences between ^ and state
        //save differences
        return;
    }
}
