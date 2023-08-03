package components;

import com.badlogic.gdx.graphics.Color;

public class RenderComponent extends Component {
    private float radius;
    private Color color;

    public RenderComponent(float radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

}
