package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.Component;
import components.PositionComponent;
import components.RenderComponent;
import entities.Entity;
import entities.Mob;
import entities.Player;
import utils.Config;

import java.util.Collection;
import java.util.Set;

public class RenderingSystem {

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

    public void update(Collection<? extends Entity> entities, float deltaTime) {
        if(entities == null)
            return;
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for(Entity entity : entities) {
            PositionComponent position = entity.positionComponent;
            RenderComponent render = entity.renderComponent;
            float renderX = position.getX() * Config.TILE_SIZE;
            float renderY = position.getY() * Config.TILE_SIZE;
            if(entity.getClass().isInstance(Mob.class)){
                render.getSprite().setRotation(-90f + ((Mob) entity).rotationComponent.getRotation());
                renderX -= Config.TILE_SIZE/2;
                renderY -= Config.TILE_SIZE/2;
            }
            render.getSprite().setPosition(renderX, renderY);
            render.getSprite().draw(spriteBatch);
        }
        spriteBatch.end();
    }

    public void updatePlayer(Player player, float deltaTime) {
        spriteBatch.begin();

        PositionComponent position = player.positionComponent;
        RenderComponent render = player.renderComponent;

        camera.position.set(position.getX() * Config.TILE_SIZE, position.getY() * Config.TILE_SIZE, 0);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        render.getSprite().setPosition(position.getX() * Config.TILE_SIZE - Config.TILE_SIZE/2, position.getY() * Config.TILE_SIZE-Config.TILE_SIZE/2);
        render.getSprite().setRotation(-90f + player.rotationComponent.getRotation());
        render.getSprite().draw(spriteBatch);

        spriteBatch.end();
    }

}
