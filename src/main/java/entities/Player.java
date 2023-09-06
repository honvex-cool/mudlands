package entities;

import actions.ActionType;
import actions.GameTimer;
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

    private final GameTimer actionCooldown = GameTimer.finished(0.2f);
    private final GameTimer regenerationCooldown = GameTimer.started(0.2f);
    private final GameTimer starvationCooldown = GameTimer.started(1f);
    private final GameTimer healCooldown = GameTimer.started(1f);

    private final ItemComponent itemComponent = () -> {
        var item = inventory.getRightHand().getClass();
        return item == NoneItem.class ? null : item;
    };

    private boolean moving = false;

    private final DecreasingHungerComponent hunger = new DecreasingHungerComponent(100, 30, 3, 5);
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
            healCooldown.restart();
            return;
        }
        starvationCooldown.restart();
        if(healCooldown.use(deltaTime))
            composition.fix(5);
    }

    private void handleStarvation(float deltaTime) {
        if(!Vital.isDrained(hunger)) {
            starvationCooldown.restart();
            return;
        }
        healCooldown.restart();
        if(starvationCooldown.use(deltaTime))
            composition.damage(5);
    }

    public void requestAction(ActionType actionType) {
        if(actionType == ActionType.HIT){
            var item = inventory.getRightHand();
            actionCooldown.useItem(item);
            inventory.checkItem();
        }
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

    public void heal(int amount) {
        composition.fix(amount);
    }
}
