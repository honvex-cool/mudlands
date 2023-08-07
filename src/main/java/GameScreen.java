import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.PlayerComponent;
import components.PositionComponent;
import components.RenderComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.EntityManager;
import generator.WorldLoader;
import systems.*;
import world.WorldMap;

import java.io.IOException;

public class GameScreen implements Screen {
    private final EntityManager entityManager;
    private final WorldLoader loader = new WorldLoader();

    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final InventoryRendering inventoryRendering;
    private final CraftingRendering craftingRendering;

    public GameScreen(MudlandsGame mudlandsGame) {
        loader.createWorld(42, "testWorld");

        WorldMap worldMap = new WorldMap(loader);

        inventoryRendering = new InventoryRendering();
        craftingRendering = new CraftingRendering();

        entityManager = new EntityManager();
        entityManager.addSystem(new GroundRenderingSystem(worldMap, spriteBatch));
        entityManager.addSystem(new RenderingSystem(spriteBatch));
        entityManager.addSystem(new MovementSystem());
        entityManager.addSystem(new InputSystem());
        entityManager.addSystem(new DeathSystem());


        Entity player = entityManager.createEntity();
        player.add(new PlayerComponent());
        player.add(new PositionComponent(0f, 0f));
        player.add(new VelocityComponent());
        player.add(new RenderComponent(50, new Texture(Gdx.files.internal("assets/textures/Player.png"))));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        entityManager.update(deltaTime);
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
