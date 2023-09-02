package systems.spawning;

import components.PositionComponent;
import entities.Hitbox;
import entities.grounds.Ground;
import entities.mobs.Mob;
import utils.Pair;

import java.util.*;

public class PlacementRules {
    private final Map<Pair<Integer, Integer>, ? extends Hitbox> passives;
    private final Map<Pair<Integer, Integer>, Ground> grounds;
    private final Map<Class<? extends Mob>, Set<Class<? extends Ground>>> forbidden = new HashMap<>();
    private final Collection<? extends Mob> mobs;

    public PlacementRules(
        Map<Pair<Integer, Integer>, ? extends Hitbox> passives,
        Map<Pair<Integer, Integer>, Ground> grounds,
        Collection<? extends Mob> mobs
    ) {
        this.passives = passives;
        this.grounds = grounds;
        this.mobs = mobs;
    }

    @SafeVarargs
    public final void forbidOn(Class<? extends Mob> mobType, Class<? extends Ground>... forbiddenGrounds) {
        forbidden.put(mobType, new HashSet<>(List.of(forbiddenGrounds)));
    }

    public boolean canMobBePlaced(Mob mob, PositionComponent positionComponent) {
        if(isForbiddenAt(mob.getClass(), positionComponent))
            return false;
        for(Mob other : mobs)
            if(other != mob && distance(other.mutablePositionComponent, positionComponent) < other.getRadius()+mob.getRadius())
                return false;
        return true;
    }

    public boolean isForbiddenAt(Class<? extends Mob> mobClass, PositionComponent positionComponent) {
        Pair<Integer, Integer> gridPosition = PositionComponent.getFieldAsPair(positionComponent);
        Hitbox passive = passives.get(gridPosition);
        if(passive != null && passive.isActive())
            return true;
        Ground ground = grounds.get(gridPosition);
        if(ground == null)
            return true;
        var forbiddenGrounds = forbidden.get(mobClass);
        if(forbiddenGrounds == null)
            return false;
        return forbiddenGrounds.contains(ground.getClass());
    }

    private static float distance(PositionComponent first, PositionComponent second) {
        float xDiff = first.getX() - second.getX();
        float yDiff = first.getY() - second.getY();
        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
