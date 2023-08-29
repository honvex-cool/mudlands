package entities;

import actions.ActionType;
import components.*;
import utils.*;

import java.util.Map;
import java.util.Set;

public class Entity implements Savable, ComponentHolder {
    public MutablePositionComponent mutablePositionComponent = new MutablePositionComponent(0, 0);
    protected MutableHealthComponent hp;
    public boolean isGenerated() {
        return false;
    }

    public Entity() {
    }

    protected Entity(MutablePositionComponent mutablePositionComponent) {
        this.mutablePositionComponent = mutablePositionComponent;
    }

    @Override
    public Map<Integer, Integer> saveData() {
        return Map.of(0,hp.getCurrentPoints());
    }

    @Override
    public void construct(Map<Integer, Integer> struct, boolean generated) {
        hp = new MutableHealthComponent(100);
        if(!struct.isEmpty()){
            hp.damage(hp.getMaxPoints() - struct.get(0));
        }
    }

    public void react(ActionType actionType, Mob actor){
        if(actionType == ActionType.HIT)
            hp.damage(actor.getAttackStrength());
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
