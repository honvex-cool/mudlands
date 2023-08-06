package entities;

import systems.GameSystem;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityManager {
    private int nextEntityId = 0;
    private final Set<GameSystem> systems = new HashSet<>();
    private final Set<Entity> entities = new HashSet<>();

    private final Set<Entity> pendingUpgraded = new HashSet<>();
    private final Set<Entity> pendingDowngraded = new HashSet<>();
    private final Set<Entity> pendingDiscarded = new HashSet<>();

    public void update(float deltaTime) {
        triggerReactions();
        for(GameSystem system : systems)
            system.update(deltaTime);
    }

    public Entity createEntity() {
        Entity entity = createEntity(null);
        entities.add(entity);
        return entity;
    }

    public Entity createEntity(String name) {
        return new Entity(this, nextEntityId++, name);
    }

    public void addSystem(GameSystem system) {
        systems.add(system);
        entities.forEach(system::reactToUpgrade);
    }

    public void removeSystem(GameSystem system) {
        systems.remove(system);
    }

    void upgrade(Entity entity) {
        pendingUpgraded.add(entity);
    }

    void downgrade(Entity entity) {
        pendingDowngraded.add(entity);
    }

    void discard(Entity entity) {
        pendingDiscarded.add(entity);
        entities.remove(entity);
    }

    private void triggerReactionsToUpgrade() {
        handleAll(pendingUpgraded, system -> system::reactToUpgrade);
    }

    private void triggerReactionsToDowngrade() {
        handleAll(pendingDowngraded, system -> system::reactToDowngrade);
    }

    private void triggerReactionsToDiscard() {
        handleAll(pendingDiscarded, system -> system::removeEntity);
    }

    private void triggerReactions() {
        triggerReactionsToDiscard();
        triggerReactionsToUpgrade();
        triggerReactionsToDowngrade();
    }

    private void handleAll(Set<Entity> pending, Function<GameSystem, Consumer<Entity>> getReaction) {
        for(GameSystem system : systems)
            pending.forEach(getReaction.apply(system));
        pending.clear();
    }
}
