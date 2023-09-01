package entities.grounds;

import actions.ActionType;
import entities.Entity;
import entities.mobs.Mob;

public abstract class Ground extends Entity {
    public float getSpeedModifier() {
        return 1;
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        return;
    }
}
