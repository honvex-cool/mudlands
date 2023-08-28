package graphics.drawable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import graphics.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public class LocalizedSprite implements Drawable {
    private final Sprite sprite;
    private Transform transform;
    private final float rotation;

    public LocalizedSprite(Sprite sprite, @NotNull Transform transform, float rotation) {
        this.sprite = sprite;
        this.transform = transform;
        this.rotation = rotation;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawSprite(sprite, getTransform(), rotation);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public void setTransform(@NotNull Transform transform) {
        this.transform = transform;
    }
}
