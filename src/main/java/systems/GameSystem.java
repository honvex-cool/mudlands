package systems;

import entities.Entity;

public interface GameSystem {
    void update(float deltaTime);

    void reactToUpgrade(Entity entity);

    void reactToDowngrade(Entity entity);

    void removeEntity(Entity entity);
}
