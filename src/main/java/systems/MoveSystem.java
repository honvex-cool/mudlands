package systems;

import entities.Ground;
import entities.Mob;
import entities.Passive;
import utils.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public class MoveSystem {
    public MoveSystem() {
    }

    public void move(Collection<Mob> mobs, Map<Pair<Integer,Integer>,Passive> passives, Map<Pair<Integer,Integer>, Ground> grounds, float deltaTime) {
        for(var mob : mobs) {
            Pair<Float, Float> velocity = mob.velocityComponent.getAsPair();
            if(velocity == null || velocity.equals(new Pair<>(0,0)))
                continue;
            float modifier = grounds.get(mob.positionComponent.getAsPair()).getSpeedModifier();
            float x = mob.positionComponent.getX();
            float y = mob.positionComponent.getY();
            float newX = x + velocity.getFirst() * deltaTime * modifier;
            float newY = y + velocity.getSecond() * deltaTime * modifier;
            Passive passive = passives.get(new Pair<>((int)Math.floor(newX),(int)Math.floor(newY)));
            if(passive != null && passive.isActive())
                continue;
            mob.positionComponent.setX(newX);
            mob.positionComponent.setY(newY);
        }
    }
}
