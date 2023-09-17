package entities.passives;

import components.MutablePositionComponent;
import entities.Entity;
import entities.materials.Composition;
import entities.materials.Mix;

public class MudPuddle extends Passive {
    public MudPuddle() {
        composition = new Composition(new Mix(0, 10, 90, 0));
    }

    @Override
    public Entity getSuccessor() {
        return new MudEssence(new MutablePositionComponent(mutablePositionComponent.getX(), mutablePositionComponent.getY()));
    }
}
