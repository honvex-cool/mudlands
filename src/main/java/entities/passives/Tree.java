package entities.passives;

import components.MutablePositionComponent;
import entities.Entity;
import entities.materials.Composition;
import entities.materials.Mix;

public class Tree extends Passive {
    public Tree() {
        composition = new Composition(new Mix(100, 0, 0, 0));
    }

    @Override
    public Entity getSuccessor() {
        return new Sticks(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }
}
