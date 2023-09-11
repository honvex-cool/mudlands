package systems.controllers;

import components.MutablePositionComponent;
import components.PositionComponent;
import entities.mobs.Mob;
import systems.spawning.PlacementRules;
import utils.Pair;

import java.util.*;

final class BfsContext {
    private final PlacementRules rules;
    private final Set<Class<? extends Mob>> specificMobTypes;
    private final Map<Pair<Integer, Integer>, Pair<Integer, Integer>> predecessor = new HashMap<>();

    BfsContext(PlacementRules rules, Set<Class<? extends Mob>> specificMobTypes) {
        this.rules = rules;
        this.specificMobTypes = specificMobTypes;
    }

    void runFrom(Pair<Integer, Integer> field, int distance) {
        predecessor.clear();
        predecessor.put(field, null);
        Queue<Pair<Integer, Integer>> pending = new ArrayDeque<>();
        pending.add(field);
        while(!pending.isEmpty()) {
            var current = pending.remove();
            for(int i = -1; i <= 1; i++) {
                for(int j = -1; j <= 1; j++) {
                    if(i == 0 && j == 0)
                        continue;
                    Pair<Integer, Integer> next = new Pair<>(current.getFirst() + i, current.getSecond() + j);
                    int xDistance = Math.abs(next.getFirst() - field.getFirst());
                    int yDistance = Math.abs(next.getSecond() - field.getSecond());
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

    Pair<Integer, Integer> getPredecessor(Pair<Integer, Integer> field) {
        return predecessor.get(field);
    }

    private boolean isIllegalPosition(Pair<Integer, Integer> position) {
        PositionComponent positionComponent = new MutablePositionComponent(position.getFirst(), position.getSecond());
        for(Class<? extends Mob> type : specificMobTypes)
            if(rules.isForbiddenAt(type, positionComponent))
                return true;
        return false;
    }
}
