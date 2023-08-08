package systems;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import entities.Hitbox;

import java.util.stream.Stream;

public class HitboxRenderingSystem {
    ShapeRenderer renderer = new ShapeRenderer();

    public void render(Stream<Hitbox> hitboxes) {
    }

    public void renderOne(Hitbox hitbox) {
        Color color = hitbox.isActive() ? Color.RED : Color.GRAY;
    }
}
