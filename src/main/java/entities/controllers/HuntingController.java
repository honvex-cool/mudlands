package entities.controllers;

import components.PositionComponent;
import components.VelocityComponent;
import utils.Pair;

import java.util.*;

public class HuntingController {
    private final Set<Pair<Integer, Integer>> occupied;
    private final Map<Pair<Integer, Integer>, Pair<Integer, Integer>> predecessor = new HashMap<>();
    private final PositionComponent hunted;
    private final int distance;

    public HuntingController(Set<Pair<Integer, Integer>> occupied, PositionComponent hunted, int distance) {
        this.occupied = occupied;
        this.hunted = hunted;
        this.distance = distance;
    }

    public VelocityComponent getVelocity(PositionComponent from) {
        var field = PositionComponent.getFieldAsPair(from);
        var target = predecessor.get(field);
        if(target == null)
            return new VelocityComponent(0, 0);
        return new VelocityComponent(target.getFirst() - field.getFirst(), target.getSecond() - field.getSecond());
    }

    public void update() {
        predecessor.clear();
        var huntedField = PositionComponent.getFieldAsPair(hunted);
        System.err.println(huntedField.getFirst() + " " + huntedField.getSecond());
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
                    if(occupied.contains(next) || predecessor.containsKey(next))
                        continue;
                    predecessor.put(next, current);
                    pending.add(next);
                }
            }
        }
    }
}
