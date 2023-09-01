package generator;

import entities.Entity;
import entities.Player;
import utils.Pair;
import utils.SaveStruct;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class WorldData implements Serializable {
    private int seed;
    private Player player;
    private Map<Pair<Integer, Integer>, Set<Entity>> changes;

    public WorldData(int seed, Player player, Map<Pair<Integer, Integer>, Set<Entity>> changes) {
        this.seed = seed;
        this.player = player;
        this.changes = changes;
    }

    public int getSeed() {
        return seed;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<Pair<Integer, Integer>, Set<Entity>> getChanges() {
        return changes;
    }
}
