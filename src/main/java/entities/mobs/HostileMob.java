package entities.mobs;

import actions.ActionType;
import actions.GameTimer;
import entities.Player;
import openable.items.Item;
import utils.Pair;

import java.util.List;

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

    @Override
    public void react(ActionType actionType, Mob actor) {
        super.react(actionType, actor);
        if(isDestroyed() && actor instanceof Player player){
            List<Pair<Item, Integer>> drops = getDrops();
            for(var drop : drops)
                player.getInventory().addItem(drop.getFirst(), drop.getSecond());
        }
    }

    protected abstract List<Pair<Item, Integer>> getDrops();
}
