package entities;

import com.badlogic.gdx.graphics.Texture;
import utils.AssetManager;
import utils.Pair;

public class Player extends Mob{

    public Player() {
        super();
    }

    public Player(AssetManager assetManager) {
        super();
        loadAssets(assetManager);
    }

    @Override
    public void updateVelocity() {
        super.updateVelocity();
    }
}
