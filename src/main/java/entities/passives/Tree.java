package entities.passives;

import actions.ActionType;
import actions.Cooldown;
import components.Component;
import components.ItemComponent;
import components.MutablePositionComponent;
import entities.Entity;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import entities.mobs.Mob;
import openable.items.Item;
import openable.items.food.AppleItem;

import java.util.Objects;
import java.util.Set;

public class Tree extends Passive {
    private final static float APPLE_GROW_TIME = 20;
    private Cooldown appleCooldown;
    private Item apple;
    private final ItemComponent itemComponent = () -> apple == null ? null : apple.getClass();

    public Tree() {
        composition = new Composition(new Mix(100, 0, 0, 0));
        growApple();
    }

    @Override
    public Entity getSuccessor() {
        return new Sticks(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(appleCooldown != null && appleCooldown.use(deltaTime))
            growApple();
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(apple != null && actor instanceof Player && actionType == ActionType.INTERACT) {
            ((Player)actor).getInventory().addItem(apple, 1);
            collectApple();
        }
    }

    private void growApple() {
        apple = new AppleItem();
        appleCooldown = null;
    }

    private void collectApple() {
        apple = null;
        appleCooldown = Cooldown.notReadyToUse(APPLE_GROW_TIME);
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, composition, itemComponent);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof Tree other)
            return Objects.equals(appleCooldown, other.appleCooldown) && Objects.equals(composition, other.composition);
        return false;
    }
}
