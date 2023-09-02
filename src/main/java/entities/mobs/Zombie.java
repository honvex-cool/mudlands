package entities.mobs;

import actions.ActionType;
import actions.Cooldown;
import entities.materials.Composition;
import entities.materials.Mix;

public class Zombie extends Mob {
    private final Cooldown hitCooldown = Cooldown.readyToUse(0.4f);

    public Zombie() {
        this.composition = new Composition(new Mix(0,0,20,80));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        hitCooldown.advance(deltaTime);
    }

    @Override
    public void requestAction(ActionType actionType) {
        if(actionType == ActionType.HIT && hitCooldown.use())
            nextAction = actionType;
    }
}
