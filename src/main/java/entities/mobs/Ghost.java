package entities.mobs;

import entities.materials.Composition;
import entities.materials.Mix;
import openable.items.Item;
import openable.items.materials.GhostEssenceItem;
import utils.Pair;

import java.util.List;

public class Ghost extends HostileMob {
    public Ghost() {
        super(1);
        this.composition = new Composition(new Mix(0, 0, 100, 200));
    }

    @Override
    protected List<Pair<Item, Integer>> getDrops() {
        return List.of(new Pair<>(new GhostEssenceItem(), 1));
    }
}
