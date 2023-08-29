package entities.passives;

import actions.ActionType;
import components.MutablePositionComponent;
import entities.Mob;
import entities.Player;
import openable.items.StickItem;
import openable.items.StoneItem;

public class Stones extends Drop {
    Stones(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player) {
            ((Player)actor).getInventory().addItem(new StoneItem(), 1);
        }
    }
}
