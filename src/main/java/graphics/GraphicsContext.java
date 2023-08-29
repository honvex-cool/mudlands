package graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import graphics.drawable.Transform;

public interface GraphicsContext {
    void drawSprite(Sprite sprite, Transform transform, float rotation, int layer);
    void placeCamera(float x, float y);

    default void begin() {
    }

    default void end() {
    }
}
