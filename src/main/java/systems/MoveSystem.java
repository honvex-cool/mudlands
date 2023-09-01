package systems;

import actions.Movement;
import components.MutablePositionComponent;
import components.PositionComponent;
import components.VelocityComponent;
import entities.Player;
import entities.grounds.Ground;
import entities.mobs.Mob;
import entities.passives.Passive;
import utils.Pair;
import utils.VectorMath;

import java.util.*;

public class MoveSystem {

    private final Map<Pair<Integer,Integer>,Passive> passives;
    private final Map<Pair<Integer,Integer>, Ground> grounds;
    private final Collection<Mob> mobs;
    public MoveSystem(Map<Pair<Integer,Integer>,Passive> passives, Map<Pair<Integer,Integer>, Ground> grounds, Collection<Mob> mobs) {
        this.passives = passives;
        this.grounds = grounds;
        this.mobs = mobs;
    }

    public void move(float deltaTime) {
        for(var mob : mobs) {
            Movement movement = mob.getMovement();
            if(movement == null)
                continue;
            VelocityComponent velocityComponent = movement.getVelocity();
            Pair<Float, Float> velocity = velocityComponent.getAsPair();
            if(velocity.getFirst() == 0 && velocity.getSecond() == 0)
                continue;
            if(!(mob instanceof Player))
                mob.rotationComponent.setRotationFromVector(velocity);

            if(tryMove(movement,mob,velocity,deltaTime))
                continue;

            float moveRotation = VectorMath.getRotationFromVector(velocity);
            float len = VectorMath.getLength(velocity);

            float mod = 11f;
            boolean success = false;
            for(int i=1;i<9;i++){
                if(tryMove(movement,mob,VectorMath.getVectorFromRotation(moveRotation+(float)i*mod,len),deltaTime)){
                    success = true;
                    break;
                }
                if(tryMove(movement,mob,VectorMath.getVectorFromRotation(moveRotation-(float)i*mod,len),deltaTime)) {
                    success = true;
                    break;
                }
            }
            if(!success)
                movement.reject();
        }
    }

    //returns true on success
    boolean tryMove(Movement movement, Mob mob, Pair<Float,Float> velocity, float deltaTime) {
        var ground = grounds.get(PositionComponent.getFieldAsPair(mob.mutablePositionComponent));
        if(ground == null)
            return false;
        float modifier = ground.getSpeedModifier();
        float x = mob.mutablePositionComponent.getX();
        float y = mob.mutablePositionComponent.getY();
        float dx = velocity.getFirst();
        float dy = velocity.getSecond();
        float length = dx * dx + dy * dy;
        int cost = (int)Math.ceil(length * deltaTime / modifier);
        if(cost >= movement.getAvailableStamina() || (dx == 0 && dy == 0))
            return false;
        float newX = x + dx * deltaTime * modifier;
        float newY = y + dy * deltaTime * modifier;
        MutablePositionComponent newPositionComponent = new MutablePositionComponent(newX, newY);
        Passive passive = passives.get(new Pair<>((int)Math.floor(newX),(int)Math.floor(newY)));
        if(passive != null && passive.isActive())
            return false;
        for(Mob other : mobs)
            if(other != mob && distance(other.mutablePositionComponent, newPositionComponent) < other.getRadius()+mob.getRadius())
                return false;
        mob.mutablePositionComponent.setX(newPositionComponent.getX());
        mob.mutablePositionComponent.setY(newPositionComponent.getY());
        movement.accept(cost);
        return true;
    }

    private static float distance(PositionComponent first, PositionComponent second) {
        float xDiff = first.getX() - second.getX();
        float yDiff = first.getY() - second.getY();
        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

}
