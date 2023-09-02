import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.*;
import entities.controllers.HuntingMovementController;
import entities.grounds.Ground;
import entities.mobs.Mob;
import entities.passives.Passive;
import generator.WorldLoader;
import graphics.GraphicsContext;
import graphics.GraphicsContextImpl;
import graphics.DrawablePresenter;
import graphics.ResolutionProvider;
import openable.OpenableManager;
import systems.*;
import utils.AssetManager;
import utils.Config;
import utils.Debug;
import utils.Pair;

import java.io.IOException;
import java.util.*;

public class GameScreen implements Screen {
    private final WorldLoader loader;
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final AssetManager assetManager = new AssetManager("assets");
    private final UniversalFactory universalFactory;
    private RenderingSystem renderingSystem;
    private InputSystem inputSystem;
    private OpenableManager openableManager;
    private final SpawnSystem spawnSystem;
    private ChunkManagerSystem chunkManagerSystem;
    private MoveSystem moveSystem;
    private ActionManagerSystem actionManagerSystem;
    private UpdateSystem updateSystem;

    private Player player;
    private Map<Pair<Integer, Integer>, Ground> ground;
    private Map<Pair<Integer, Integer>, Passive> passives;
    private Collection<Mob> mobs;

    public GameScreen(MudlandsGame mudlandsGame) {
        universalFactory = new UniversalFactory(
            EntityMappings.GROUND_MAP,
            EntityMappings.PASSIVE_MAP
        );

        loader = new WorldLoader(universalFactory);

        if(Debug.LOAD_WORLD) {
            try {
                loader.loadWorld("testWorld");
                player = loader.loadPlayer();
            } catch(IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            loader.createWorld(42, "testWorld");
            player = loader.loadPlayer();
        }

        inputSystem = new InputSystem();
        openableManager = new OpenableManager(inputSystem, player, assetManager);

        actionManagerSystem = new ActionManagerSystem();

        ground = new HashMap<>();
        passives = new HashMap<>();
        mobs = new ArrayList<>();

        chunkManagerSystem = new ChunkManagerSystem(player, loader, ground, passives, mobs);

        HuntingMovementController controller = new HuntingMovementController(
            Collections.unmodifiableSet(passives.keySet()),
            player.mutablePositionComponent,
            30
        );
        spawnSystem = new SpawnSystem(mobs, controller);

        Collection<Ground> groundsView = Collections.unmodifiableCollection(ground.values());
        Collection<Passive> passivesView = Collections.unmodifiableCollection(passives.values());
        Collection<Mob> mobsView = Collections.unmodifiableCollection(mobs);

        moveSystem = new MoveSystem(Collections.unmodifiableMap(passives), Collections.unmodifiableMap(ground), mobsView);

        updateSystem = new UpdateSystem(
            player,
            groundsView,
            passivesView,
            mobsView
        );

        GraphicsContext graphicsContext = new GraphicsContextImpl(
            spriteBatch,
            new OrthographicCamera(),
            new ResolutionProvider(),
            Config.TILES_ON_SCREEN
        );

        renderingSystem = new RenderingSystem(
            graphicsContext,
            new DrawablePresenter(assetManager),
            player,
            groundsView,
            passivesView,
            mobsView
        );

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateSystem.update(delta);
        chunkManagerSystem.update();
        inputSystem.update(player, delta);
        spawnSystem.update(delta);
        actionManagerSystem.update(player, passives, mobs);
        mobs.add(player);
        moveSystem.move(delta);
        mobs.remove(player);
        renderingSystem.update();
        openableManager.update();
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
        chunkManagerSystem.unloadAll();
        loader.savePlayer(player);
        try {
            loader.saveWorld();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
