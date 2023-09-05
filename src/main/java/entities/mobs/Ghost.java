package entities.mobs;

import actions.ActionType;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import openable.items.materials.GhostEssenceItem;
import openable.items.materials.ZombieBloodItem;

public class Ghost extends Mob {
    public Ghost() {
        this.composition = new Composition(new Mix(0, 0, 100, 200));
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(isDestroyed() && actor instanceof Player player){
            player.getInventory().addItem(new GhostEssenceItem(), 1);
        }
    }
}
