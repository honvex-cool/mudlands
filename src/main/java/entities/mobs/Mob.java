package entities.mobs;

import actions.ActionType;
import components.Component;
import components.MutablePositionComponent;
import components.MutableRotationComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.Hitbox;

import java.util.Set;

public class Mob extends Entity implements Hitbox {
    public VelocityComponent velocityComponent;
    public MutableRotationComponent rotationComponent;
    public ActionType nextAction = null;
    private float moveSpeed;
    public int attackStrength = 6;

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

    public void updateVelocity() {
    }
    public int getAttackStrength(){
        return attackStrength;
    }
    public ActionType getNextAction(){
        return nextAction;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, rotationComponent);
    }
}
