package systems.spawning;

import actions.Repeatable;
import actions.RepeatableManager;
import components.PositionComponent;
import entities.mobs.Mob;
import org.jetbrains.annotations.NotNull;
import systems.controllers.Controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MobControlSystem {
    private final Collection<Mob> mobs;
    private final RepeatableManager spawnManager = new RepeatableManager();
    private final PositionComponent playerPosition;
    private final int limit;
    private final Map<Class<? extends Mob>, Controller> controllers = new HashMap<>();

    public MobControlSystem(PositionComponent playerPosition, Collection<Mob> mobs, int limit) {
        this.playerPosition = playerPosition;
        this.mobs = mobs;
        if(limit <= 0)
            throw new IllegalArgumentException("`limit` must not be negative");
        this.limit = limit;
    }

    public void addSpawningRule(float interval, @NotNull Function<PositionComponent, Mob> provider) {
        spawnManager.add(new Repeatable(interval, () -> tryAddMob(provider.apply(playerPosition))));
    }

    public void registerController(Class<? extends Mob> mobClass, Controller controller) {
        if(!controller.canControl(mobClass))
            throw new IllegalArgumentException("`controller` must be able to control mobs of class `mobClass`");
        controllers.put(mobClass, controller);
    }

    public void update(float deltaTime) {
        for(Mob mob : mobs) {
            Controller controller = controllers.get(mob.getClass());
            if(controller != null)
                controller.control(mob);
        }
        spawnManager.update(deltaTime);
    }

    private void tryAddMob(Mob mob) {
        if(mob != null && mobs.size() < limit)
            mobs.add(mob);
    }
}
