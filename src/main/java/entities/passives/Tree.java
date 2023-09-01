package entities.passives;

import actions.ActionType;
import components.MutablePositionComponent;
import entities.Entity;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import entities.mobs.Mob;
import openable.items.food.AppleItem;
import openable.items.materials.StickItem;

public class Tree extends Passive {
    public Tree() {
        composition = new Composition(new Mix(100, 0, 0, 0));
    }

    @Override
    public Entity getSuccessor() {
        return new Sticks(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actor instanceof Player && actionType == ActionType.INTERACT) {
            ((Player)actor).getInventory().addItem(new AppleItem(), 1);
        }
    }
}
