package entities;

import actions.ActionType;
import actions.Cooldown;
import actions.Movement;
import components.*;
import entities.materials.Composition;
import entities.materials.Damage;
import entities.materials.Mix;
import entities.mobs.Mob;
import openable.inventory.Inventory;
import openable.items.NoneItem;

import java.util.Set;

public class Player extends Mob {

    private final Inventory inventory = new Inventory();

    private final Cooldown actionCooldown = Cooldown.readyToUse(0.2f);
    private final Cooldown regenerationCooldown = Cooldown.notReadyToUse(0.2f);
    private final Cooldown starvationCooldown = Cooldown.notReadyToUse(1f);
    private final Cooldown healCooldown = Cooldown.notReadyToUse(1f);

    private final ItemComponent itemComponent = () -> {
        var item = inventory.getRightHand().getClass();
        return item == NoneItem.class ? null : item;
    };

    private boolean moving = false;

    private final DecayingHungerComponent hunger = new DecayingHungerComponent(100, 30, 3, 5);
    private final MutableStaminaComponent stamina = new MutableStaminaComponent(1000);

    public Player(){
        super();
        this.composition = new Composition(new Mix(0,0,10,90),200);
    }

    @Override
    public Damage getAttackDamage(){
        return inventory.getRightHand().getAttackStrength();
    }
    @Override
    public void update(float deltaTime) {
        actionCooldown.advance(deltaTime);
        if(!moving && regenerationCooldown.use(deltaTime))
            stamina.fix(1000);
        hunger.update(deltaTime);
        handleHealing(deltaTime);
        handleStarvation(deltaTime);
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

    private void handleHealing(float deltaTime) {
        if(!Vital.isSatisfied(hunger)) {
            healCooldown.reset();
            return;
        }
        starvationCooldown.reset();
        if(healCooldown.use(deltaTime))
            composition.fix(5);
    }

    private void handleStarvation(float deltaTime) {
        if(!Vital.isDrained(hunger)) {
            starvationCooldown.reset();
            return;
        }
        healCooldown.reset();
        if(starvationCooldown.use(deltaTime))
            composition.damage(5);
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
        return Set.of(
            mutablePositionComponent,
            rotationComponent,
            itemComponent,
            stamina,
            hunger,
            composition
        );
    }

    public void feed(int amount){
        hunger.fix(amount);
    }
}
