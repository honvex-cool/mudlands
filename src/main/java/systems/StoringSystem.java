package systems;

import entities.Entity;

import java.util.HashSet;
import java.util.Set;

public abstract class StoringSystem implements GameSystem {
    protected final Set<Entity> entities = new HashSet<>();

    @Override
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
}
