package entities.passives;

import components.MutablePositionComponent;
import entities.Entity;

public class Rock extends Passive {
    @Override
    public Entity getSuccessor() {
        return new Stones(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }
}
