package graphics;

import com.badlogic.gdx.Gdx;

public class ResolutionProvider {
    public int getWidth() {
        return Gdx.graphics.getWidth();
    }

    public int getHeight() {
        return Gdx.graphics.getHeight();
    }
}
