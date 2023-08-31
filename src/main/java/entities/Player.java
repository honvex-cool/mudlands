package entities;

import actions.ActionType;
import actions.Cooldown;
import components.Component;
import components.ItemComponent;
import components.MutableHealthComponent;
import entities.mobs.Mob;
import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.NoneItem;

import java.util.Set;

public class Player extends Mob {

    private Inventory inventory = new Inventory();
    private final Cooldown hitCooldown = Cooldown.readyToUse(0.2f);
    private final ItemComponent itemComponent = () -> {
        var item = inventory.getRightHand().getClass();
        return item == NoneItem.class ? null : item;
    };

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


    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, rotationComponent, itemComponent);
    }
}
