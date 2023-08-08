package components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RenderComponent extends Component {
    private float size;
    private Sprite sprite;

    public RenderComponent(float size, Sprite sprite) {
        this.size = size;
        this.sprite = sprite;
        this.sprite.setSize(size,size);
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
