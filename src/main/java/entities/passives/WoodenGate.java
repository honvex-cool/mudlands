package entities.passives;

import actions.ActionType;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import entities.mobs.Mob;
import openable.items.structures.WoodenGateItem;

public class WoodenGate extends Passive{
    private boolean open = false;
    public WoodenGate(){
        composition = new Composition(new Mix(100, 0, 0, 0));
    }
    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player) {
            if(isDestroyed()) {
                ((Player)actor).getInventory().addItem(new WoodenGateItem(), 1);
            }
            else if(actionType == ActionType.INTERACT){
                open = !open;
            }
        }
    }
    @Override
    public String getLogicalName() {
        if(open)
            return "openwoodengate";
        else
            return "closedwoodengate";
    }

    @Override
    public boolean isActive() {
        return !open;
    }
}
