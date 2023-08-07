package entities;

import com.badlogic.gdx.graphics.Texture;
import inventory.Inventory;

public class Player extends Mob{
    private Inventory inventory;
    public Player(float x, float y, Texture texture){
        super(x,y,texture,0);
        inventory = new Inventory();
    }
}
