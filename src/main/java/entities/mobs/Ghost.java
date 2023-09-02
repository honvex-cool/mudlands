package entities.mobs;

import entities.materials.Composition;
import entities.materials.Mix;

public class Ghost extends Mob {
    public Ghost() {
        this.composition = new Composition(new Mix(0, 0, 100, 200));
    }
}
