package systems.controllers;

import actions.ActionType;
import components.PositionComponent;
import components.VelocityComponent;
import entities.mobs.Mob;
import systems.spawning.PlacementRules;
import utils.Pair;
import utils.VectorMath;

import java.util.*;

public class HuntingController implements Controller {
    private final Set<Class<? extends Mob>> hunters = new HashSet<>();
    private final PositionComponent hunted;
    private Pair<Integer, Integer> last;
    private final int distance;
    private final List<GridSearch> searches;

    public HuntingController(
        List<PlacementRules> placementRules,
        PositionComponent hunted,
        int distance
    ) {
        this.searches = placementRules.stream().map(
            rules -> new GridSearch(rules, Collections.unmodifiableSet(hunters))
        ).toList();
        this.hunted = hunted;
        this.distance = distance;
        update();
    }

    @Override
    public void control(Mob mob) {
        if(VectorMath.distance(mob.mutablePositionComponent.getPosition(), hunted.getPosition()) <= 1.5)
            mob.requestAction(ActionType.HIT);
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

    private void update() {
        var huntedField = PositionComponent.getFieldAsPair(hunted);
        searches.forEach(search -> search.runFrom(huntedField, distance));
    }

    private VelocityComponent getVelocity(PositionComponent from) {
        var huntedField = PositionComponent.getFieldAsPair(hunted);
        if(!huntedField.equals(last))
            update();
        last = huntedField;
        var fromField = PositionComponent.getFieldAsPair(from);
        Pair<Integer, Integer> target = null;
        for(GridSearch search : searches) {
            target = search.getPredecessor(fromField);
            if(target != null)
                break;
        }
        if(target == null)
            return new VelocityComponent(0, 0);
        return new VelocityComponent(target.getFirst() - fromField.getFirst(), target.getSecond() - fromField.getSecond());
    }
}
