package entities.passives;

import actions.ActionType;
import components.MutablePositionComponent;
import entities.Entity;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import entities.mobs.Mob;
import openable.items.food.AppleItem;

public class MudPuddle extends Passive {
    public MudPuddle() {
        composition = new Composition(new Mix(0, 10, 90, 0));
    }

    @Override
    public Entity getSuccessor() {
        return new MudEssence(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }
}
