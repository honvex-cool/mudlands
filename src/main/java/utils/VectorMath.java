package utils;

import components.PositionComponent;

public class VectorMath {
    public static float distance(Pair<Float,Float> first, Pair<Float,Float> second) {
        float xDiff = first.getFirst() - second.getFirst();
        float yDiff = first.getSecond() - second.getSecond();
        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public static float getRotationFromVector(Pair<Float,Float> vector){
        if(vector.getFirst() == 0 && vector.getSecond() == 0){
            return 0f;
        }
        double len = Math.sqrt(Math.pow(vector.getFirst(),2)+Math.pow(vector.getSecond(), 2));
        float rotation = (float) Math.toDegrees(Math.asin(-vector.getFirst()/(len))) + 90f;
        if(vector.getSecond()<0){
            rotation = 360f - rotation;
        }
        return rotation;
    }

    public static Pair<Float,Float> getVectorFromRotation(float rotation,float scale){
        float dx = (float) Math.cos(Math.toRadians(rotation))*scale;
        float dy = (float) Math.sin(Math.toRadians(rotation))*scale;
        return new Pair<>(dx,dy);
    }

    public static float getLength(Pair<Float,Float> vector){
        return (float) Math.sqrt(vector.getFirst()*vector.getFirst() + vector.getSecond()*vector.getSecond());
    }
}
