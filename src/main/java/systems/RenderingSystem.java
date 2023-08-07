package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.Component;
import components.PlayerComponent;
import components.PositionComponent;
import components.RenderComponent;
import entities.Entity;

import java.util.Set;

public class RenderingSystem extends RepetitiveSystem {

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private static final Set<Class<? extends Component>> REQUIRED_COMPONENTS = Set.of(
        PositionComponent.class,
        RenderComponent.class
    );

    public RenderingSystem(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
    }


    @Override
    protected void begin() {
        spriteBatch.begin();
    }

    @Override
    public void updateOne(Entity entity, float deltaTime) {
        float tileSize = Gdx.graphics.getWidth() / 16f;
        PositionComponent position = entity.get(PositionComponent.class);
        RenderComponent render = entity.get(RenderComponent.class);

        if(entity.has(PlayerComponent.class)) {
            camera.position.set(position.getX() * tileSize, position.getY() * tileSize, 0);
            camera.update();
            spriteBatch.setProjectionMatrix(camera.combined);
        }

        render.getSprite().setPosition(position.getX() * tileSize, position.getY() * tileSize);
        render.getSprite().setSize(tileSize / 2, tileSize / 2);
        render.getSprite().draw(spriteBatch);
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }

    @Override
    protected Set<Class<? extends Component>> requirements() {
        return REQUIRED_COMPONENTS;
    }
}
