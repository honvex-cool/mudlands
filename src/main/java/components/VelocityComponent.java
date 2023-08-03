package components;

public class VelocityComponent extends Component {
    private float x;
    private float y;

    public VelocityComponent() {
        x = 0;
        y = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
