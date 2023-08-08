package components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RenderComponent extends Component {
    private float size;
    private Sprite sprite;

    private Texture texture;

    public RenderComponent(float size, Texture texture) {
        this.size = size;
        this.texture = texture;
        this.sprite = new Sprite(texture);
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

    public Texture getTexture() {
        return texture;
    }

}
