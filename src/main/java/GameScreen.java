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
import utils.Config;
import world.WorldMap;

public class GameScreen implements Screen {
    private final EntityManager entityManager;

    private final SpriteBatch spriteBatch = new SpriteBatch();

    public GameScreen(MudlandsGame mudlandsGame) {
        WorldLoader loader = new WorldLoader();
        loader.createWorld(Config.WORLD_SEED, Config.WORLD_NAME);

        WorldMap worldMap = new WorldMap(loader);

        entityManager = new EntityManager();
        entityManager.addSystem(new RenderingSystem(spriteBatch));
        entityManager.addSystem(new MovementSystem());
        entityManager.addSystem(new InputSystem());
        entityManager.addSystem(new DeathSystem());
        entityManager.addSystem(new GroundRenderingSystem(worldMap, spriteBatch));

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
    }
}
