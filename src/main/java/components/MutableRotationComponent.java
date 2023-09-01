 package components;

import utils.Pair;
import utils.VectorMath;

 public class MutableRotationComponent implements RotationComponent {
    private float rotation=90; //in degrees

    @Override
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation){
        this.rotation = rotation%360f;
        if(this.rotation < 0){
            this.rotation += 360f;
        }
    }

    public void setRotationFromVector(Pair<Float,Float> vector){
        if(vector.getFirst() == 0 && vector.getSecond() == 0){
            return;
        }
        rotation = VectorMath.getRotationFromVector(vector);
    }
}
