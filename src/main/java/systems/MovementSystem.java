package systems;

import components.Component;
import components.PositionComponent;
import components.VelocityComponent;
import entities.Entity;

import java.util.Set;

public class MovementSystem extends RepetitiveSystem {
    public static final Set<Class<? extends Component>> REQUIRED_COMPONENTS = Set.of(
        PositionComponent.class,
        VelocityComponent.class
    );

    @Override
    public void updateOne(Entity entity, float deltaTime) {
        PositionComponent position = entity.get(PositionComponent.class);
        VelocityComponent velocity = entity.get(VelocityComponent.class);
        position.setX(position.getX() + velocity.getX() * deltaTime);
        position.setY(position.getY() + velocity.getY() * deltaTime);
    }

    @Override
    protected Set<Class<? extends Component>> requirements() {
        return REQUIRED_COMPONENTS;
    }
}
