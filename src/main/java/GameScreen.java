import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import components.PositionComponent;
import components.RenderComponent;
import components.VelocityComponent;
import entities.Entity;
import entities.World;
import systems.InputSystem;
import systems.MovementSystem;
import systems.RenderingSystem;

public class GameScreen implements Screen {

    private final MudlandsGame game;

    private World world;

    private RenderingSystem renderingSystem;
    private MovementSystem movementSystem;

    private InputSystem inputSystem;

    public GameScreen(MudlandsGame mudlandsGame) {
        game = mudlandsGame;
        world = new World();
        renderingSystem = new RenderingSystem(world);
        movementSystem = new MovementSystem(world);
        inputSystem = new InputSystem(world);

        Entity player = world.createEntity();
        player.add(new PositionComponent(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f));
        player.add(new VelocityComponent());
        player.add(new RenderComponent(50, Color.RED));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        inputSystem.update(deltaTime);
        movementSystem.update(deltaTime);
        renderingSystem.update(deltaTime);
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
