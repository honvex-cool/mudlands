package systems.controllers;

import components.MutablePositionComponent;
import components.PositionComponent;
import components.VelocityComponent;
import entities.mobs.Mob;
import systems.spawning.PlacementRules;
import utils.Pair;
import utils.VectorMath;

import java.util.*;

public class HuntingController implements Controller {
    private final Set<Class<? extends Mob>> hunters = new HashSet<>();
    private final Map<Pair<Integer, Integer>, Pair<Integer, Integer>> predecessor = new HashMap<>();
    private final PlacementRules placementRules;
    private final PositionComponent hunted;
    private final int distance;
    private Pair<Integer, Integer> last;

    public HuntingController(PlacementRules placementRules, PositionComponent hunted, int distance) {
        this.placementRules = placementRules;
        this.hunted = hunted;
        this.distance = distance;
    }

    @Override
    public void control(Mob mob) {
        mob.velocityComponent = getVelocity(mob.mutablePositionComponent);
        Pair<Float, Float> difference = new Pair<>(
            hunted.getX() - mob.mutablePositionComponent.getX(),
            hunted.getY() - mob.mutablePositionComponent.getY()
        );
        mob.rotationComponent.setRotation(VectorMath.getRotationFromVector(difference));
    }

    @Override
    public boolean canControl(Class<? extends Mob> mobClass) {
        return hunters.contains(mobClass);
    }

    public void addHunter(Class<? extends Mob> hunter) {
        hunters.add(hunter);
    }

    private VelocityComponent getVelocity(PositionComponent from) {
        var field = PositionComponent.getFieldAsPair(from);
        if(!field.equals(last)) {
            update();
            last = field;
        }
        var target = predecessor.get(field);
        if(target == null)
            return new VelocityComponent(0, 0);
        return new VelocityComponent(target.getFirst() - field.getFirst(), target.getSecond() - field.getSecond());
    }

    private void update() {
        predecessor.clear();
        var huntedField = PositionComponent.getFieldAsPair(hunted);
        predecessor.put(huntedField, null);
        Queue<Pair<Integer, Integer>> pending = new ArrayDeque<>();
        pending.add(PositionComponent.getFieldAsPair(hunted));
        while(!pending.isEmpty()) {
            var current = pending.remove();
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(i == 0 && j == 0)
                        continue;
                    Pair<Integer, Integer> next = new Pair<>(current.getFirst() + i, current.getSecond() + j);
                    int xDistance = Math.abs(next.getFirst() - huntedField.getFirst());
                    int yDistance = Math.abs(next.getSecond() - huntedField.getSecond());
                    if(xDistance + yDistance > distance)
                        continue;
                    if(predecessor.containsKey(next))
                        continue;
                    if(isIllegalPosition(next))
                        continue;
                    predecessor.put(next, current);
                    pending.add(next);
                }
            }
        }
    }

    private boolean isIllegalPosition(Pair<Integer, Integer> next) {
        PositionComponent positionComponent = new MutablePositionComponent(next.getFirst(), next.getSecond());
        for(Class<? extends Mob> hunter : hunters)
            if(placementRules.isForbiddenAt(hunter, positionComponent))
                return true;
        return false;
    }
}
