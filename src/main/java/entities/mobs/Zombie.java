package entities.mobs;

import entities.materials.Composition;
import entities.materials.Mix;

public class Zombie extends Mob {
    public Zombie() {
        this.composition = new Composition(new Mix(0,0,20,80));
    }
}
