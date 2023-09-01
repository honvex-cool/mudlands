package entities;

import actions.ActionType;
import components.*;
import entities.mobs.Mob;
import utils.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class Entity implements Serializable, ComponentHolder {
    public MutablePositionComponent mutablePositionComponent = new MutablePositionComponent(0, 0);
    protected MutableHealthComponent hp = new MutableHealthComponent(100);
    public Entity() {
    }

    protected Entity(MutablePositionComponent mutablePositionComponent) {
        this.mutablePositionComponent = mutablePositionComponent;
    }
    public void react(ActionType actionType, Mob actor){
    }

    public void update(float deltaTime) {
    }

    public boolean isDestroyed(){
        return Vital.isDrained(hp);
    }

    public Entity getSuccessor(){
        return null;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent);
    }

    public MutableHealthComponent getHp() {
        return hp;
    }
}
