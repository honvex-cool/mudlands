package entities.mobs;

import entities.materials.Composition;
import entities.materials.Mix;

public class Pig extends Mob {
    public Pig() {
        this.composition = new Composition(new Mix(0,0,0,100));
    }
}
