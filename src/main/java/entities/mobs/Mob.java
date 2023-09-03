package entities.mobs;

import actions.ActionType;
import actions.Movement;
import components.Component;
import components.MutablePositionComponent;
import components.MutableRotationComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.Hitbox;
import entities.materials.Damage;
import utils.Pair;
import utils.VectorMath;

import java.util.Set;

public abstract class Mob extends Entity implements Hitbox {
    public VelocityComponent velocityComponent;
    public MutableRotationComponent rotationComponent;
    public ActionType nextAction = null;
    private Damage damage = new Damage(5, 5, 5, 5);

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

    public void setAttackDamage(Damage damage) {
        this.damage = damage;
    }

    public Damage getAttackDamage(){
        return damage;
    }

    public ActionType getNextAction(){
        return nextAction;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, rotationComponent,composition);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(actionType == ActionType.HIT){
            composition.damage(actor.getAttackDamage());
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
}
