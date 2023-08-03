package systems;

import entities.EntityManager;

public abstract class System {
    protected EntityManager entityManager;

    public System(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract void update(float deltaTime);
}
