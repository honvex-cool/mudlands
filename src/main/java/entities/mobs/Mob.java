package entities.mobs;

import actions.ActionType;
import actions.Movement;
import components.Component;
import components.MutablePositionComponent;
import components.MutableRotationComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.Hitbox;
import entities.Player;
import entities.materials.Damage;
import openable.items.Item;
import openable.items.NoneItem;
import utils.Pair;
import utils.VectorMath;

import java.util.List;
import java.util.Set;

public abstract class Mob extends Entity implements Hitbox {
    public VelocityComponent velocityComponent;
    public MutableRotationComponent rotationComponent;
    public ActionType nextAction = null;
    private final Damage damage = new Damage(5, 5, 5, 5);

    public Mob() {
        this(0,0);
    }

    public Mob(float x, float y){
        mutablePositionComponent = new MutablePositionComponent(x, y);
        velocityComponent = new VelocityComponent();
        rotationComponent = new MutableRotationComponent();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public Movement getMovement() {
        return new Movement(velocityComponent, Integer.MAX_VALUE, null);
    }

    public Damage getAttackDamage(){
        return damage;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, rotationComponent,composition);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actionType == ActionType.HIT){
            composition.damage(actor.getAttackDamage().withResistance(getAttackResistance()));
        }
        if(isDestroyed() && actor instanceof Player player){
            List<Pair<Item, Integer>> drops = getDrops();
            for(var drop : drops)
                player.getInventory().addItem(drop.getFirst(), drop.getSecond());
        }
    }

    @Override
    public float getRadius() {
        return 0.45f;
    }

    protected final void setVelocity(float speed, float direction) {
        Pair<Float, Float> velocity = VectorMath.getVectorFromRotation(direction, speed);
        velocityComponent.setX(velocity.getFirst());
        velocityComponent.setY(velocity.getSecond());
        rotationComponent.setRotation(direction);
    }

    protected final void halt() {
        setVelocity(0, 0);
    }

    public void requestAction(ActionType actionType) {
    }

    protected List<Pair<Item, Integer>> getDrops(){
        return List.of(new Pair<>(new NoneItem(), 0));
    }

    protected Damage getAttackResistance() {
        return null;
    }
}
