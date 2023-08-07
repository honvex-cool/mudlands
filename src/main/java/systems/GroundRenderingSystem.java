package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.Component;
import components.PlayerComponent;
import components.PositionComponent;
import entities.Entity;
import generator.GroundType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*public class GroundRenderingSystem extends RepetitiveSystem {
    private static final Set<Class<? extends Component>> REQUIRED_COMPONENTS = Set.of(
        PlayerComponent.class,
        PositionComponent.class
    );

    private final SpriteBatch spriteBatch;

    private final WorldMap worldMap;
    private final Map<GroundType, Sprite> spriteMap = new HashMap<>();

    private float tileSize;

    public GroundRenderingSystem(WorldMap worldMap, SpriteBatch spriteBatch) {
        this.worldMap = worldMap;
        this.spriteBatch = spriteBatch;
        for(GroundType type : GroundType.values())
            spriteMap.put(
                type,
                new Sprite(new Texture(Gdx.files.internal("assets/textures/" + type.name() + ".png")))
            );
    }

    @Override
    protected void begin() {
        tileSize = Gdx.graphics.getWidth() / 16f;
        spriteBatch.begin();
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }

    @Override
    protected void updateOne(Entity player, float deltaTime) {
        /*PositionComponent position = player.get(PositionComponent.class);
        int row = (int)Math.floor(position.getY());
        int column = (int)Math.floor(position.getX());
        for(int r = -9; r <= 9; r++) {
            for(int c = -16; c <= 16; c++) {
                Sprite sprite = spriteMap.get(worldMap.query(row + r, column + c));
                sprite.setPosition((column + c) * tileSize, (row + r) * tileSize);
                sprite.setSize(tileSize, tileSize);
                sprite.draw(spriteBatch);
            }
        }
    }

    @Override
    protected Set<Class<? extends Component>> requirements() {
        return REQUIRED_COMPONENTS;
    }
}*/
