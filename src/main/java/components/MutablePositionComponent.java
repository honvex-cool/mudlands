package components;

public class MutablePositionComponent implements PositionComponent {
    private float x;
    private float y;

    public MutablePositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
}
