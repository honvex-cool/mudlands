package generator;

import utils.Pair;
import utils.SaveStruct;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class WorldData implements Serializable {
    private int seed;
    private Map<Pair<Integer, Integer>, Set<SaveStruct>> changes;

    public WorldData(int seed, Map<Pair<Integer, Integer>, Set<SaveStruct>> changes) {
        this.seed = seed;
        this.changes = changes;
    }

    public int getSeed() {
        return seed;
    }

    public Map<Pair<Integer, Integer>, Set<SaveStruct>> getChanges() {
        return changes;
    }
}
