package systems.controllers;

import entities.mobs.Mob;
import systems.spawning.PlacementRules;
import utils.Pair;
import utils.VectorMath;

import java.util.*;

record Route(Pair<Integer, Integer> field, Pair<Integer, Integer> predecessor, float distance) {
}

final class GridSearch {

    private final PlacementRules rules;
    private final Set<Class<? extends Mob>> specificMobTypes;
    private final Queue<Route> routes = new PriorityQueue<>(Comparator.comparing(Route::distance));
    private final Map<Pair<Integer, Integer>, Pair<Integer, Integer>> predecessors = new HashMap<>();
    private int distanceLimit;

    GridSearch(PlacementRules rules, Set<Class<? extends Mob>> specificMobTypes) {
        this.rules = rules;
        this.specificMobTypes = specificMobTypes;
    }

    void runFrom(Pair<Integer, Integer> field, int distanceLimit) {
        if(distanceLimit < 0)
            throw new IllegalArgumentException("`distanceLimit` must not be negative");
        this.distanceLimit = distanceLimit;
        clear();
        routes.add(new Route(field, field, 0));
        while(!routes.isEmpty())
            processFront();
    }

    Pair<Integer, Integer> getPredecessor(Pair<Integer, Integer> field) {
        return predecessors.get(field);
    }

    private void processFront() {
        Route front = routes.remove();
        if(predecessors.containsKey(front.field()))
            return;
        predecessors.put(front.field(), front.predecessor());
        scheduleRoutes(front.field(), front.distance());
    }

    private void scheduleRoutes(Pair<Integer, Integer> field, float currentDistance) {
        for(int i = -1; i <= 1; i++)
            for(int j = -1; j <= 1; j++)
                if(i != 0 || j != 0)
                    inspectEdge(field, new Pair<>(field.getFirst() + i, field.getSecond() + j), currentDistance);
    }

    private void inspectEdge(Pair<Integer, Integer> from, Pair<Integer, Integer> to, float currentDistance) {
        float totalDistance = currentDistance + VectorMath.distance(from, to);
        if(totalDistance <= distanceLimit && !predecessors.containsKey(to) && isLegalPosition(to))
            routes.add(new Route(to, from, totalDistance));
    }

    private void clear() {
        predecessors.clear();
        routes.clear();
    }

    private boolean isLegalPosition(Pair<Integer, Integer> position) {
        if(rules.isOccupied(position))
            return false;
        for(Class<? extends Mob> type : specificMobTypes)
            if(rules.isForbiddenAt(type, position))
                return false;
        return true;
    }
}
