package entities.grounds;

import actions.ActionType;
import entities.Entity;
import entities.Mob;

public abstract class Ground extends Entity {
    private float speedModifier = 1f;
    public float getSpeedModifier() {
        return speedModifier;
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        return;
    }
}
