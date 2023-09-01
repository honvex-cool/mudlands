package utils;

import components.PositionComponent;

public class VectorMath {
    public static float distance(Pair<Float,Float> first, Pair<Float,Float> second) {
        float xDiff = first.getFirst() - second.getFirst();
        float yDiff = first.getSecond() - second.getSecond();
        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}
