package entities.passives;

import actions.ActionType;
import components.Component;
import components.MutablePositionComponent;
import entities.mobs.Mob;

import java.util.Map;
import java.util.Set;

public abstract class Drop extends Passive {
    private boolean pickedUp = false;

    public Drop() {
    }

    public Drop(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        if(actionType == ActionType.INTERACT)
            pickedUp = true;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isDestroyed() {
        return pickedUp;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent);
    }
}
