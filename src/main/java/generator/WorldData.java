package generator;

import utils.Pair;

import java.io.Serializable;
import java.util.Map;

public class WorldData implements Serializable {
    private int seed;
    private Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, FieldStruct>> changes;

    public WorldData(int seed, Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, FieldStruct>> changes) {
        this.seed = seed;
        this.changes = changes;
    }

    public int getSeed() {
        return seed;
    }

    public Map<Pair<Integer, Integer>, Map<Pair<Integer, Integer>, FieldStruct>> getChanges() {
        return changes;
    }
}
