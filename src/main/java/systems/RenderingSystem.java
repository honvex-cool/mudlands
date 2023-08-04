package systems;

import components.Component;
import components.PositionComponent;
import components.RenderComponent;
import entities.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Set;

public class RenderingSystem extends RepetitiveSystem {
    private static final Set<Class<? extends Component>> REQUIRED_COMPONENTS = Set.of(
        PositionComponent.class,
        RenderComponent.class
    );

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    protected void begin() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    public void updateOne(Entity entity, float deltaTime) {
        RenderComponent circleRender = entity.get(RenderComponent.class);
        PositionComponent position = entity.get(PositionComponent.class);
        shapeRenderer.setColor(circleRender.getColor());
        shapeRenderer.circle(position.getX(), position.getY(), circleRender.getRadius());
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }

    @Override
    protected Set<Class<? extends Component>> requirements() {
        return REQUIRED_COMPONENTS;
    }
}
