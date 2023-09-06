package entities.mobs;

import entities.materials.Composition;
import entities.materials.Mix;
import openable.items.Item;
import openable.items.materials.ZombieBloodItem;
import utils.Pair;

import java.util.List;

public class Zombie extends HostileMob {

    public Zombie() {
        super(0.5f);
        this.composition = new Composition(new Mix(0,0,20,80));
    }

    @Override
    protected List<Pair<Item, Integer>> getDrops() {
        return List.of(new Pair<>(new ZombieBloodItem(), 1));
    }
}
