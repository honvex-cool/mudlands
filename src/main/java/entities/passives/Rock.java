package entities.passives;

import components.MutablePositionComponent;
import entities.Entity;
import entities.materials.Composition;
import entities.materials.Mix;

public class Rock extends Passive {
    public Rock() {
        composition = new Composition(new Mix(0, 90, 10, 0));
    }

    @Override
    public Entity getSuccessor() {
        return new Stones(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }
}
