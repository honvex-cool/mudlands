package entities.passives;

import actions.ActionType;
import components.MutablePositionComponent;
import entities.Player;
import entities.mobs.Mob;
import openable.items.materials.MudEssenceItem;
import openable.items.materials.StickItem;

public class MudEssence extends Drop {
    MudEssence(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player && actionType == ActionType.INTERACT) {
            ((Player)actor).getInventory().addItem(new MudEssenceItem(), 1);
        }
    }
}
