package graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.drawable.Transform;
import utils.AssetManager;

import java.util.*;

public class GraphicsContextImpl implements GraphicsContext {
    private final SpriteBatch spriteBatch;
    private final OrthographicCamera camera;
    private final AssetManager assetManager;
    private final float tileSize;
    private final Map<Integer, List<Runnable>> layers = new TreeMap<>();

    public GraphicsContextImpl(
        SpriteBatch spriteBatch,
        OrthographicCamera camera,
        ResolutionProvider resolutionProvider,
        AssetManager assetManager,
        int tilesOnScreen
    ) {
        this.spriteBatch = spriteBatch;
        this.camera = camera;
        float width = resolutionProvider.getWidth();
        float height = resolutionProvider.getHeight();
        this.camera.setToOrtho(false, width, height);
        this.camera.update();
        this.assetManager = assetManager;
        tileSize = width / tilesOnScreen;
    }

    @Override
    public void drawSprite(String name, Transform transform, float rotation, float alpha, int layer) {
        Sprite sprite = assetManager.getSprite(name);
        float x = transform.x() * tileSize;
        float y = transform.y() * tileSize;
        float width = transform.width() * tileSize;
        float height = transform.height() * tileSize;
        Runnable drawing = () -> {
            sprite.setPosition(x, y);
            sprite.setSize(width, height);
            sprite.setRotation(rotation);
            sprite.setAlpha(alpha);
            sprite.draw(spriteBatch);
        };
        layers.computeIfAbsent(layer, unused -> new ArrayList<>()).add(drawing);
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
        for(List<Runnable> layer : layers.values()) {
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
