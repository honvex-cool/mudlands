package entities.passives;

import actions.ActionType;
import components.Component;
import components.MutablePositionComponent;
import components.Vital;
import entities.Entity;
import entities.Hitbox;
import entities.materials.Composition;
import entities.mobs.Mob;

import java.util.Map;
import java.util.Set;

public class Passive extends Entity implements Hitbox {
    public Passive() {
    }
    protected Passive(MutablePositionComponent mutablePositionComponent) {
        super(mutablePositionComponent);
    }
    @Override
    public void react(ActionType actionType, Mob actor) {
        if(actionType == ActionType.HIT)
            composition.damage(actor.getAttackDamage());
    }

    @Override
    public Set<Component> viewComponents() {
        return Set.of(mutablePositionComponent, composition);
    }
}
