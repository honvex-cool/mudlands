package entities.passives;

import actions.ActionType;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import entities.mobs.Mob;
import openable.items.structures.StoneFenceItem;

public class StoneFence extends Passive{
    public StoneFence(){
        composition = new Composition(new Mix(0, 100, 0, 0));
    }
    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player && isDestroyed()) {
            ((Player)actor).getInventory().addItem(new StoneFenceItem(), 1);
        }
    }
}
