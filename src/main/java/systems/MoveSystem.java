package systems;

import components.MutablePositionComponent;
import components.PositionComponent;
import entities.grounds.Ground;
import entities.mobs.Mob;
import entities.passives.Passive;
import utils.Pair;

import java.util.*;

public class MoveSystem {
    public MoveSystem() {
    }

    public void move(
        Collection<Mob> mobs,
        Map<Pair<Integer,Integer>,Passive> passives,
        Map<Pair<Integer,Integer>, Ground> grounds,
        float deltaTime
    ) {
        for(var mob : mobs) {
            Pair<Float, Float> velocity = mob.velocityComponent.getAsPair();
            mob.rotationComponent.setRotationFromVector(velocity);
            if(velocity == null || velocity.equals(new Pair<>(0,0)))
                continue;
            if(!tryMove(mob,velocity,passives,grounds,mobs,deltaTime)){
                if(!tryMove(mob,new Pair<>(velocity.getFirst(),0f),passives,grounds,mobs,deltaTime)){
                    tryMove(mob,new Pair<>(0f,velocity.getSecond()),passives,grounds,mobs,deltaTime);
                }
            }
        }
    }

    //returns true on success
    boolean tryMove(
        Mob mob,
        Pair<Float,Float> velocity,
        Map<Pair<Integer,Integer>,Passive> passives,
        Map<Pair<Integer,Integer>, Ground> grounds,
        Collection<Mob> mobs,
        float deltaTime
    ) {
        float modifier = grounds.get(PositionComponent.getFieldAsPair(mob.mutablePositionComponent)).getSpeedModifier();
        float x = mob.mutablePositionComponent.getX();
        float y = mob.mutablePositionComponent.getY();
        float newX = x + velocity.getFirst() * deltaTime * modifier;
        float newY = y + velocity.getSecond() * deltaTime * modifier;
        MutablePositionComponent newPositionComponent = new MutablePositionComponent(newX, newY);
        Passive passive = passives.get(new Pair<>((int)Math.floor(newX),(int)Math.floor(newY)));
        if(passive != null && passive.isActive())
            return false;
        for(Mob other : mobs)
            if(other != mob && squaredDistance(other.mutablePositionComponent, newPositionComponent) < 1)
                return false;
        mob.mutablePositionComponent.setX(newPositionComponent.getX());
        mob.mutablePositionComponent.setY(newPositionComponent.getY());
        return true;
    }

    private static float squaredDistance(PositionComponent first, PositionComponent second) {
        float xDiff = first.getX() - second.getX();
        float yDiff = first.getY() - second.getY();
        return xDiff * xDiff + yDiff * yDiff;
    }
}
