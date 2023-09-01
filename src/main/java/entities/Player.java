package entities;

import actions.ActionType;
import actions.Cooldown;
import actions.Movement;
import components.*;
import entities.mobs.Mob;
import openable.inventory.Inventory;
import openable.items.NoneItem;

import java.util.Set;

public class Player extends Mob {

    private Inventory inventory = new Inventory();
    private final Cooldown actionCooldown = Cooldown.readyToUse(0.2f);
    private final Cooldown regenerationCooldown = Cooldown.readyToUse(0.2f);
    private final ItemComponent itemComponent = () -> {
        var item = inventory.getRightHand().getClass();
        return item == NoneItem.class ? null : item;
    };
    private boolean moving = false;

    private final MutableStaminaComponent stamina = new MutableStaminaComponent(1000);

    public Player(){
        super();
        hp = new MutableHealthComponent(100);
    }

    @Override
    public void update(float deltaTime) {
        actionCooldown.advance(deltaTime);
        if(!moving && regenerationCooldown.use(deltaTime))
            stamina.fix(1000);
    }

    @Override
    public Movement getMovement() {
        if(Vital.isDrained(stamina) || velocityComponent.getX() == 0 && velocityComponent.getY() == 0) {
            moving = false;
            return null;
        }
        return new Movement(velocityComponent, stamina.getCurrentPoints(), this::becomeTired);
    }

    private void becomeTired(int amount) {
        stamina.damage(amount);
        moving = true;
    }

    @Override
    public void updateVelocity() {
        super.updateVelocity();
    }

    public void requestAction(ActionType actionType) {
        if(actionCooldown.use())
            nextAction = actionType;
    }
    public Inventory getInventory() {
        return inventory;
    }


    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, rotationComponent, itemComponent, stamina);
    }
}
