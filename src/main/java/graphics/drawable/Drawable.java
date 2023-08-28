package graphics.drawable;

import graphics.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public interface Drawable {
    void draw(GraphicsContext graphicsContext);
    Transform getTransform();
    void setTransform(@NotNull Transform transform);
}
