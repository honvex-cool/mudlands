package entities.mobs;

import actions.ActionType;
import actions.Cooldown;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import openable.items.materials.ZombieBloodItem;

public class Zombie extends Mob {
    private final Cooldown hitCooldown = Cooldown.readyToUse(0.4f);

    public Zombie() {
        this.composition = new Composition(new Mix(0,0,20,80));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        hitCooldown.advance(deltaTime);
    }

    @Override
    public void requestAction(ActionType actionType) {
        if(actionType == ActionType.HIT && hitCooldown.use())
            nextAction = actionType;
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(isDestroyed() && actor instanceof Player player){
            player.getInventory().addItem(new ZombieBloodItem(), 1);
        }
    }
}
