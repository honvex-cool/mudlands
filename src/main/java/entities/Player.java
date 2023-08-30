package entities;

import actions.ActionType;
import actions.Cooldown;
import components.MutableHealthComponent;
import entities.mobs.Mob;
import openable.inventory.Inventory;

public class Player extends Mob {

    private Inventory inventory = new Inventory();
    private final Cooldown hitCooldown = new Cooldown(0.2f);

    public Player(){
        super();
        hp = new MutableHealthComponent(100);
    }

    @Override
    public void update(float deltaTime) {
        hitCooldown.advance(deltaTime);
    }

    @Override
    public void updateVelocity() {
        super.updateVelocity();
    }

    public void requestAction(ActionType actionType) {
        if(actionType == ActionType.HIT && hitCooldown.use())
            nextAction = actionType;
    }
    public Inventory getInventory() {
        return inventory;
    }

}
