package entities.passives;

import components.MutablePositionComponent;
import entities.Entity;

public class Tree extends Passive {
    @Override
    public Entity getSuccessor() {
        return new Sticks(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }
}
