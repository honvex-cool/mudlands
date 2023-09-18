package entities.mobs;

import actions.ActionType;
import actions.GameTimer;

public abstract class HostileMob extends Mob {
    private final GameTimer hitCooldown;

    protected HostileMob(float hitCooldownTime) {
        hitCooldown = GameTimer.finished(hitCooldownTime);
    }

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
