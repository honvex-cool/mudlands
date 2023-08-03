package systems;

import components.PositionComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.EntityManager;

import java.util.List;

public class MovementSystem extends System {
    public MovementSystem(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void update(float deltaTime) {
        List<Entity> entities = entityManager.getEntitiesWithComponents(PositionComponent.class, VelocityComponent.class);

        for (Entity entity : entities) {
            PositionComponent position = entity.get(PositionComponent.class);
            VelocityComponent velocity = entity.get(VelocityComponent.class);

            position.setX(position.getX() + velocity.getX() * deltaTime);
            position.setY(position.getY() + velocity.getY() * deltaTime);
        }
    }
}
