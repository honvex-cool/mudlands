package entities;

import actions.ActionType;
import components.Component;
import components.ComponentHolder;
import components.MutablePositionComponent;
import utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Entity implements Savable, ComponentHolder {
    public MutablePositionComponent mutablePositionComponent = new MutablePositionComponent(0, 0);
    protected int hp;
    protected SaveStruct successor=null;
    protected SaveStruct defaultSuccessor = new SaveStruct(EntityTag.NONE,0, 0, 0, new HashMap<>());
    public boolean isGenerated() {
        return false;
    }

    @Override
    public Map<Integer, Integer> saveData() {
        return Map.of(0,hp);
    }

    @Override
    public void construct(Map<Integer, Integer> struct, boolean generated) {
        hp = 100;
        if(!struct.isEmpty()){
            hp = struct.get(0);
        }
    }

    public void react(ActionType actionType, Mob actor){
        if(actionType == ActionType.HIT){
            hp -= actor.getAttackStrength();
            if(hp <=0 ){
                defaultSuccessor.x = mutablePositionComponent.getX();
                defaultSuccessor.y = mutablePositionComponent.getY();
                successor = defaultSuccessor;
            }
        }
    }

    public void update(float deltaTime) {
    }

    public boolean isDestroyed(){
        return (successor != null);
    }
    public SaveStruct getSuccessor(){
        return successor;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent);
    }
}
