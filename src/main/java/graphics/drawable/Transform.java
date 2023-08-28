package graphics.drawable;

public record Transform(float x, float y, float width, float height) {
    public Transform() {
        this(0, 0, 1, 1);
    }

    public Transform withX(float x) {
        return new Transform(x, y, width, height);
    }

    public Transform shiftedX(float shift) {
        return withX(x + shift);
    }

    public Transform withY(float y) {
        return new Transform(x, y, width, height);
    }

    public Transform shiftedY(float shift) {
        return withY(y + shift);
    }

    public Transform withPosition(float x, float y) {
        return new Transform(x, y, width, height);
    }

    public Transform shifted(float xShift, float yShift) {
        return shiftedX(xShift).shiftedY(yShift);
    }

    public Transform withWidth(float width) {
        return new Transform(x, y, width, height);
    }

    public Transform withHeight(float height) {
        return new Transform(x, y, width, height);
    }

    public Transform withDimensions(float width, float height) {
        return new Transform(x, y, width, height);
    }
}
