package entities.passives;

import actions.ActionType;
import components.Component;
import entities.Entity;
import entities.Hitbox;
import entities.Mob;

import java.util.Map;
import java.util.Set;

public class Passive extends Entity implements Hitbox {
    protected boolean generated;
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
