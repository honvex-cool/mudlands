package entities.passives;

import actions.ActionType;
import entities.Mob;

public class Sticks extends Passive{
    @Override
    public void react(ActionType actionType, Mob actor) {
        return;
    }

    @Override
    public boolean isActive() {
        return false;
    }
}
