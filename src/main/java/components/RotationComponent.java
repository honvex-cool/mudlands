 package components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import utils.Pair;

public class RotationComponent extends Component{
    private float rotation=90; //in degrees

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
        double len = Math.sqrt(Math.pow(vector.getFirst(),2)+Math.pow(vector.getSecond(), 2));
        rotation = (float) Math.toDegrees(Math.asin(-vector.getFirst()/(len))) + 90f;
        if(vector.getSecond()<0){
            rotation = 360f - rotation;
        }
    }
}
