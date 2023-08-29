package graphics.drawable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import graphics.GraphicsContext;

public class LocalizedSprite extends PlaceholderDrawable {
    private final Sprite sprite;
    private final float rotation;
    private final int layer;

    public LocalizedSprite(Transform transform, Sprite sprite, int layer) {
        this(transform, sprite, layer, 0);
    }

    public LocalizedSprite(Transform transform, Sprite sprite, int layer, float rotation) {
        super(transform);
        this.sprite = sprite;
        setTransform(transform);
        this.layer = layer;
        this.rotation = rotation;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawSprite(sprite, getTransform(), rotation, layer);
    }
}
