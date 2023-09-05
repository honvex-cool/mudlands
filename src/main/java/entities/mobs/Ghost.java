package entities.mobs;

import actions.ActionType;
import actions.Cooldown;
import entities.Player;
import entities.materials.Composition;
import entities.materials.Mix;
import openable.items.materials.GhostEssenceItem;

public class Ghost extends Mob {
    private final Cooldown hitCooldown = Cooldown.readyToUse(0.4f);

    public Ghost() {
        this.composition = new Composition(new Mix(0, 0, 100, 200));
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        hitCooldown.advance(deltaTime);
    }

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(isDestroyed() && actor instanceof Player player){
            player.getInventory().addItem(new GhostEssenceItem(), 1);
        }
    }

    @Override
    public void requestAction(ActionType actionType) {
        if(actionType == ActionType.HIT && hitCooldown.use())
            nextAction = actionType;
    }
}
