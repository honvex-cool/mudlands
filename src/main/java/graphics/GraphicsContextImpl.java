package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.drawable.Transform;

public class GraphicsContextImpl implements GraphicsContext {
    private final SpriteBatch spriteBatch;
    private final OrthographicCamera camera;
    private final float tileSize;

    public GraphicsContextImpl(SpriteBatch spriteBatch, int tilesOnScreen) {
        this.spriteBatch = spriteBatch;
        camera = new OrthographicCamera();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera.setToOrtho(false, width, height);
        camera.update();
        tileSize = width / tilesOnScreen;
    }

    @Override
    public void drawSprite(Sprite sprite, Transform transform, float rotation) {
        float x = transform.x() * tileSize;
        float y = transform.y() * tileSize;
        sprite.setPosition(x, y);
        float width = transform.width() * tileSize;
        float height = transform.height() * tileSize;
        sprite.setSize(width, height);
        sprite.setRotation(rotation);
        sprite.draw(spriteBatch);
    }

    @Override
    public void placeCamera(float x, float y) {
        camera.position.set(x * tileSize, y * tileSize, 0);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void begin() {
        spriteBatch.begin();
    }

    @Override
    public void end() {
        spriteBatch.end();
    }
}
