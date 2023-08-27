package systems;

import components.PositionComponent;
import entities.grounds.Ground;
import entities.Mob;
import entities.passives.Passive;
import utils.Pair;

import java.util.Collection;
import java.util.Map;

public class MoveSystem {
    public MoveSystem() {
    }

    public void move(Collection<Mob> mobs, Map<Pair<Integer,Integer>,Passive> passives, Map<Pair<Integer,Integer>, Ground> grounds, float deltaTime) {
        for(var mob : mobs) {
            Pair<Float, Float> velocity = mob.velocityComponent.getAsPair();
            mob.rotationComponent.setRotationFromVector(velocity);
            if(velocity == null || velocity.equals(new Pair<>(0,0)))
                continue;
            if(!tryMove(mob,velocity,passives,grounds,deltaTime)){
                if(!tryMove(mob,new Pair<>(velocity.getFirst(),0f),passives,grounds,deltaTime)){
                    tryMove(mob,new Pair<>(0f,velocity.getSecond()),passives,grounds,deltaTime);
                }
            }
        }
    }

    //returns true on success
    boolean tryMove(Mob mob, Pair<Float,Float> velocity, Map<Pair<Integer,Integer>,Passive> passives, Map<Pair<Integer,Integer>, Ground> grounds, float deltaTime){
        float modifier = grounds.get(PositionComponent.getFieldAsPair(mob.mutablePositionComponent)).getSpeedModifier();
        float x = mob.mutablePositionComponent.getX();
        float y = mob.mutablePositionComponent.getY();
        float newX = x + velocity.getFirst() * deltaTime * modifier;
        float newY = y + velocity.getSecond() * deltaTime * modifier;
        Passive passive = passives.get(new Pair<>((int)Math.floor(newX),(int)Math.floor(newY)));
        if(passive != null && passive.isActive())
            return false;
        mob.mutablePositionComponent.setX(newX);
        mob.mutablePositionComponent.setY(newY);
        return true;
    }
}
