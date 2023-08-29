package graphics.drawable;

import graphics.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public class PlaceholderDrawable implements Drawable {
    private Transform transform;

    public PlaceholderDrawable(@NotNull Transform transform) {
        this.transform = transform;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
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
