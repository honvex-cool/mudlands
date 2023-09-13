package systems.spawning;

import components.PositionComponent;
import entities.Hitbox;
import entities.grounds.Ground;
import entities.mobs.Mob;
import utils.Pair;
import utils.VectorMath;

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
        Pair<Integer, Integer> field = PositionComponent.getFieldAsPair(positionComponent);
        if(isForbiddenAt(mob.getClass(), field))
            return false;
        if(isOccupied(field) && mob.isActive())
            return false;
        for(Mob other : mobs) {
            if(other == mob || !other.isActive())
                continue;
            if(VectorMath.distance(other.mutablePositionComponent.getPosition(), positionComponent.getPosition()) < other.getRadius() + mob.getRadius())
                return false;
        }
        return true;
    }

    public boolean isForbiddenAt(Class<? extends Mob> mobClass, Pair<Integer, Integer> gridPosition) {
        Ground ground = grounds.get(gridPosition);
        if(ground == null)
            return true;
        var forbiddenGrounds = forbidden.get(mobClass);
        if(forbiddenGrounds == null)
            return false;
        return forbiddenGrounds.contains(ground.getClass());
    }

    public boolean isOccupied(Pair<Integer, Integer> gridPosition) {
        Hitbox passive = passives.get(gridPosition);
        return passive != null && passive.isActive();
    }
}
