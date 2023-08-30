package entities.passives;

import actions.ActionType;
import components.Component;
import components.MutablePositionComponent;
import entities.Entity;
import entities.Hitbox;
import entities.mobs.Mob;

import java.util.Map;
import java.util.Set;

public class Passive extends Entity implements Hitbox {
    protected boolean generated;

    public Passive() {
    }

    protected Passive(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }

    @Override
    public void construct(Map<Integer, Integer> struct, boolean generated) {
        super.construct(struct, generated);
        this.generated = generated;
    }

    @Override
    public boolean isGenerated() {
        return generated;
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        generated = false;
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, hp);
    }
}
