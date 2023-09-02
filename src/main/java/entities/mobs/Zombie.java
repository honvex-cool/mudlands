package entities.mobs;

import actions.ActionType;
import entities.materials.Composition;
import entities.materials.Mix;

public class Zombie extends Mob {
    public Zombie() {
        this.composition = new Composition(new Mix(0,0,20,80));
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
    }
}
