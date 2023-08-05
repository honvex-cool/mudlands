package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import components.Component;
import components.PositionComponent;
import components.RenderComponent;
import entities.Entity;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Set;

public class RenderingSystem extends RepetitiveSystem {

    private OrthographicCamera camera;
    private static final Set<Class<? extends Component>> REQUIRED_COMPONENTS = Set.of(
        PositionComponent.class,
        RenderComponent.class
    );

    public RenderingSystem() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        camera.update();
    }

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    protected void begin() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    public void updateOne(Entity entity, float deltaTime) {
        RenderComponent circleRender = entity.get(RenderComponent.class);
        PositionComponent position = entity.get(PositionComponent.class);
        camera.position.set(position.getX(), position.getY(), 0);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(200f, 300f, 10);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(400f, 300f, 10);

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
