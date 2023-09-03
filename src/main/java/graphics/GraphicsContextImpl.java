package graphics;

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
    private final List<List<Runnable>> layers = new ArrayList<>();

    public GraphicsContextImpl(
        SpriteBatch spriteBatch,
        OrthographicCamera camera,
        ResolutionProvider resolutionProvider,
        int tilesOnScreen
    ) {
        this.spriteBatch = spriteBatch;
        this.camera = camera;
        float width = resolutionProvider.getWidth();
        float height = resolutionProvider.getHeight();
        this.camera.setToOrtho(false, width, height);
        this.camera.update();
        tileSize = width / tilesOnScreen;
        for(int i = 0; i < 10; i++)
            layers.add(new ArrayList<>());
    }

    @Override
    public void drawSprite(Sprite sprite, Transform transform, float rotation, int layer) {
        float x = transform.x() * tileSize;
        float y = transform.y() * tileSize;
        float width = transform.width() * tileSize;
        float height = transform.height() * tileSize;
        Runnable drawing = () -> {
            sprite.setPosition(x, y);
            sprite.setSize(width, height);
            sprite.setRotation(rotation);
            sprite.draw(spriteBatch);
        };
        layers.get(layer).add(drawing);
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
        for(List<Runnable> layer : layers) {
            for(Runnable drawing : layer)
                drawing.run();
            layer.clear();
        }
        spriteBatch.end();
    }
    @Override
    public void dispose(){
        spriteBatch.dispose();
    }
}
