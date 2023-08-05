import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import components.PlayerComponent;
import components.PositionComponent;
import components.RenderComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.World;
import systems.DeathSystem;
import systems.InputSystem;
import systems.MovementSystem;
import systems.RenderingSystem;

public class GameScreen implements Screen {
    private final World world;

    public GameScreen(MudlandsGame mudlandsGame) {
        world = new World();
        world.addSystem(new RenderingSystem());
        world.addSystem(new MovementSystem());
        world.addSystem(new InputSystem());
        world.addSystem(new DeathSystem());
        Entity player = world.createEntity();
        player.add(new PlayerComponent());
        player.add(new PositionComponent(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f));
        player.add(new VelocityComponent());
        player.add(new RenderComponent(50, Color.RED));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        world.update(deltaTime);
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
    }
}
