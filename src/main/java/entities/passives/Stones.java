package entities.passives;

import actions.ActionType;
import components.MutablePositionComponent;
import entities.mobs.Mob;
import entities.Player;
import openable.items.materials.StoneItem;

public class Stones extends Drop {
    Stones(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player && actionType == ActionType.INTERACT) {
            ((Player)actor).getInventory().addItem(new StoneItem(), 1);
        }
    }
}
