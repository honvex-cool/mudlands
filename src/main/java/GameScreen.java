import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.PlayerComponent;
import components.PositionComponent;
import components.RenderComponent;
import components.VelocityComponent;
import entities.*;
import generator.GroundType;
import generator.WorldLoader;
import inventory.InventoryRendering;
import systems.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

public class GameScreen implements Screen {
    private final WorldLoader loader = new WorldLoader();
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final InventoryRendering inventoryRendering;
    private final CraftingRendering craftingRendering;
    private RenderingSystem renderingSystem;
    private InputSystem inputSystem;
    private ChunkManagerSystem chunkManagerSystem;
    private MoveSystem moveSystem;

    private Player player;
    private Collection<Ground> ground;
    private Collection<Passive> passives;
    private Collection<Mob> mobs;

    public GameScreen(MudlandsGame mudlandsGame) {

        loader.createWorld(42, "testWorld");

        inventoryRendering = new InventoryRendering();
        craftingRendering = new CraftingRendering();

        player = new Player(0,0,new Texture(Gdx.files.internal("assets/textures/Player.png")));

        renderingSystem = new RenderingSystem(spriteBatch);
        inputSystem = new InputSystem();
        chunkManagerSystem = new ChunkManagerSystem(player,loader);
        moveSystem = new MoveSystem();

        ground = new ArrayList<>();
        passives = new ArrayList<>();
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
        System.err.println(" " + delta + "   " + passives.size());
        inputSystem.update(player, delta);
        mobs.add(player);
        moveSystem.move(mobs, passives, delta);;
        mobs.remove(player);
        renderingSystem.update(ground, delta);
        renderingSystem.update(passives,delta);
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
        try {
            loader.saveWorld();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
