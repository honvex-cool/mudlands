package generator;

import entities.Entity;
import entities.Player;
import utils.Pair;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class GameData implements Serializable {
    private final long seed;
    private final Player player;
    private final Map<Pair<Integer, Integer>, Set<Entity>> changes;

    public GameData(long seed, Player player, Map<Pair<Integer, Integer>, Set<Entity>> changes) {
        this.seed = seed;
        this.player = player;
        this.changes = changes;
    }

    public long getSeed() {
        return seed;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<Pair<Integer, Integer>, Set<Entity>> getChanges() {
        return changes;
    }
}
