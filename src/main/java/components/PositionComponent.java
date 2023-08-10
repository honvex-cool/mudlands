package components;

import com.badlogic.gdx.math.Vector2;
import utils.Config;
import utils.Pair;

import java.util.Vector;

public class PositionComponent extends Component {
    private float x;
    private float y;

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
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

    public int getChunkX(){return (int)Math.floor(x/ Config.CHUNK_SIZE);}
    public int getChunkY(){return (int)Math.floor(y/ Config.CHUNK_SIZE);}

    public Pair<Integer,Integer> getChunk(){return new Pair<>(getChunkX(),getChunkY());}
    public Pair<Integer,Integer> getAsPair(){return new Pair<>((int)Math.floor(getX()),(int)Math.floor(getY()));}
}
