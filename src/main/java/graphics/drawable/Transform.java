package graphics.drawable;

import utils.Pair;

import java.util.Collection;

public record Transform(float x, float y, float width, float height) {
    public Transform {
        if(width < 0)
            throw new IllegalArgumentException("`width` must not be negative");
        if(height < 0)
            throw new IllegalArgumentException("`height` must not be negative");
    }

    public Transform() {
        this(0, 0, 1, 1);
    }

    public float right() {
        return x + width;
    }

    public float top() {
        return y + height;
    }

    public Pair<Float, Float> center() {
        return new Pair<>(x + width / 2, y + height / 2);
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

    public Transform scaledWidth(float factor) {
        return new Transform(x, y, width * factor, height);
    }

    public Transform withHeight(float height) {
        return new Transform(x, y, width, height);
    }

    public Transform scaledHeight(float factor) {
        return new Transform(x, y, width, height * factor);
    }

    public Transform withDimensions(float width, float height) {
        return new Transform(x, y, width, height);
    }

    public Transform scaled(float widthFactor, float heightFactor) {
        return new Transform(x, y, width * widthFactor, height * heightFactor);
    }

    public Transform leftAlignedOnTopOf(Transform other) {
        return withPosition(other.x, other.top());
    }

    public Transform rightAlignedOnTopOf(Transform other) {
        return withPosition(other.right() - width, other.top());
    }

    public Transform centeredOnTopOf(Transform other) {
        return withPosition(other.x + (other.width - width) / 2, other.top());
    }

    public Transform bottomAlignedRightOf(Transform other) {
        return withPosition(other.right(), other.y);
    }

    public Transform topAlignedRightOf(Transform other) {
        return withPosition(other.right(), other.top() - height);
    }

    public Transform centeredRightOf(Transform other) {
        return withPosition(other.right(), other.y + (other.height - height) / 2);
    }

    public Transform withCenterAtCenterOf(Transform other) {
        return withPosition(other.x + (other.width - width) / 2, other.y + (other.height - height) / 2);
    }

    public static Transform boundingBox(Collection<Transform> transforms) {
        if(transforms.isEmpty())
            return null;
        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float maxRight = Float.NEGATIVE_INFINITY;
        float maxTop = Float.NEGATIVE_INFINITY;
        for(Transform transform : transforms) {
            minX = Math.min(minX, transform.x());
            minY = Math.min(minY, transform.y());
            maxRight = Math.max(maxRight, transform.right());
            maxTop = Math.max(maxTop, transform.top());
        }
        return new Transform(minX, minY, maxRight - minX, maxTop - minY);
    }
}
