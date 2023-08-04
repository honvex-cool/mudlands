package systems;

import entities.Entity;

public interface System {
    void update(float deltaTime);

    void reactToUpgrade(Entity entity);

    void reactToDowngrade(Entity entity);

    void removeEntity(Entity entity);
}
