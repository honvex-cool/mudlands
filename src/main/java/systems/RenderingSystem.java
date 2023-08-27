package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.MutablePositionComponent;
import entities.Entity;
import entities.Player;
import graphics.Presenter;
import utils.Config;

import java.util.Collection;

public class RenderingSystem {

    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final Presenter<Sprite> spritePresenter;

    public RenderingSystem(SpriteBatch spriteBatch, Presenter<Sprite> spritePresenter) {
        this.spriteBatch = spriteBatch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        this.spritePresenter = spritePresenter;
    }

    public void update(Collection<? extends Entity> entities, float deltaTime) {
        if(entities == null)
            return;
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        for(Entity entity : entities) {
            for(Sprite sprite : spritePresenter.present(entity)) {
                float newX = sprite.getX() * Config.TILE_SIZE;
                float newY = sprite.getY() * Config.TILE_SIZE;
                sprite.setPosition(newX, newY);
                sprite.draw(spriteBatch);
            }
        }
        spriteBatch.end();
    }

    public void updatePlayer(Player player, float deltaTime) {
        MutablePositionComponent position = player.mutablePositionComponent;
        camera.position.set(position.getX() * Config.TILE_SIZE, position.getY() * Config.TILE_SIZE, 0);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

}
