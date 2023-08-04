package systems;

import components.PositionComponent;
import components.RenderComponent;
import entities.Entity;
import entities.World;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class RenderingSystem extends System {
    private ShapeRenderer shapeRenderer;

    public RenderingSystem(World world) {
        super(world);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(float deltaTime) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        List<Entity> entities = world.getEntitiesWithComponents(RenderComponent.class);

        for(Entity entity : entities) {
            RenderComponent circleRender = entity.get(RenderComponent.class);
            PositionComponent position = entity.get(PositionComponent.class);

            shapeRenderer.setColor(circleRender.getColor());
            shapeRenderer.circle(position.getX(), position.getY(), circleRender.getRadius());
        }

        shapeRenderer.end();

    }
}
