package graphics;

import graphics.drawable.Transform;

public interface GraphicsContext {
    void drawSprite(String name, Transform transform, float rotation, float alpha, int layer);

    void placeCamera(float x, float y);

    default void begin() {
    }

    default void end() {
    }

    void dispose();
}
