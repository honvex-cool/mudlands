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
        updateVelocity();
    }

    public Movement getMovement() {
        return new Movement(velocityComponent, Integer.MAX_VALUE, null);
    }

    public void updateVelocity() {
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
}
