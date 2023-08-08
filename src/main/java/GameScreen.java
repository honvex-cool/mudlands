import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.*;
import entities.grounds.Ground;
import entities.passives.Passive;
import generator.WorldLoader;
import inventory.InventoryRendering;
import systems.*;
import utils.AssetManager;
import utils.Debug;
import utils.Pair;

import java.io.IOException;
import java.util.*;

public class GameScreen implements Screen {
    private final WorldLoader loader = new WorldLoader();
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final AssetManager assetManager = new AssetManager("assets");
    private final UniversalLoader entityLoader;
    private final InventoryRendering inventoryRendering;
    private final CraftingRendering craftingRendering;
    private RenderingSystem renderingSystem;
    private InputSystem inputSystem;
    private ChunkManagerSystem chunkManagerSystem;
    private MoveSystem moveSystem;

    private Player player;
    private Map<Pair<Integer,Integer>, Ground> ground;
    private Map<Pair<Integer,Integer>, Passive> passives;
    private Collection<Mob> mobs;

    public GameScreen(MudlandsGame mudlandsGame) {
        entityLoader = new UniversalLoader(
            EntityMappings.GROUND_MAP,
            EntityMappings.PASSIVE_MAP,
            EntityMappings.MOB_MAP,
            assetManager
        );

        if(Debug.LOAD_WORLD) {
            try {
                loader.loadWorld("testWorld");
                player = entityLoader.loadPlayer(loader.getPlayerSaveStruct());
            } catch(IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            loader.createWorld(42, "testWorld");
            player = new Player(assetManager);
        }

        inventoryRendering = new InventoryRendering();
        craftingRendering = new CraftingRendering();

        renderingSystem = new RenderingSystem(spriteBatch);
        inputSystem = new InputSystem();


        chunkManagerSystem = new ChunkManagerSystem(player,loader,entityLoader);
        moveSystem = new MoveSystem();


        ground = new HashMap<>();
        passives = new HashMap<>();
        mobs = new ArrayList<>();
        //ground.add(new Ground(0,0,new Texture(Gdx.files.internal("assets/textures/WATER.png")),0));

        //entityManager.addSystem(new GroundRenderingSystem(worldMap, spriteBatch));
        //entityManager.addSystem(new DeathSystem());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        chunkManagerSystem.update(ground,passives,mobs);
        Debug.log(delta, passives.size());
        inputSystem.update(player, delta);
        mobs.add(player);
        moveSystem.move(mobs, passives, ground,delta);;
        mobs.remove(player);
        renderingSystem.update(ground.values(), delta);
        renderingSystem.update(passives.values(),delta);
        renderingSystem.updatePlayer(player,delta);
        inventoryRendering.oneTick();
        craftingRendering.oneTick(); //TODO add one class that manages opening crafting and inventory or make systems for them
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        assetManager.dispose();
        loader.setPlayerSaveStruct(entityLoader.savePlayer(player));
        try {
            loader.saveWorld();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
