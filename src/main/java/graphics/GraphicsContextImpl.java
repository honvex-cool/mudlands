package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.drawable.Transform;

import java.util.ArrayList;
import java.util.List;

public class GraphicsContextImpl implements GraphicsContext {
    private final SpriteBatch spriteBatch;
    private final OrthographicCamera camera;
    private final float tileSize;
    private final List<List<Sprite>> layers = new ArrayList<>();

    public GraphicsContextImpl(SpriteBatch spriteBatch, int tilesOnScreen) {
        this.spriteBatch = spriteBatch;
        camera = new OrthographicCamera();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera.setToOrtho(false, width, height);
        camera.update();
        tileSize = width / tilesOnScreen;
        for(int i = 0; i < 10; i++)
            layers.add(new ArrayList<>());
    }

    @Override
    public void drawSprite(Sprite sprite, Transform transform, float rotation, int layer) {
        Sprite toDraw = new Sprite(sprite);
        float x = transform.x() * tileSize;
        float y = transform.y() * tileSize;
        toDraw.setPosition(x, y);
        float width = transform.width() * tileSize;
        float height = transform.height() * tileSize;
        toDraw.setSize(width, height);
        toDraw.setRotation(rotation);
        layers.get(layer).add(toDraw);
    }

    @Override
    public void placeCamera(float x, float y) {
        camera.position.set(x * tileSize, y * tileSize, 0);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void end() {
        spriteBatch.begin();
        for(List<Sprite> layer : layers) {
            for(Sprite sprite : layer)
                sprite.draw(spriteBatch);
            layer.clear();
        }
        spriteBatch.end();
    }
}
