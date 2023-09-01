package entities.passives;

import actions.ActionType;
import components.MutablePositionComponent;
import entities.mobs.Mob;
import entities.Player;
import openable.items.materials.StickItem;

public class Sticks extends Drop {
    Sticks(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player && actionType == ActionType.INTERACT) {
            ((Player)actor).getInventory().addItem(new StickItem(), 1);
        }
    }
}
