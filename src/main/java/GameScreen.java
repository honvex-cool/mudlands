import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.*;
import entities.mobs.Ghost;
import entities.mobs.Zombie;
import systems.controllers.CluelessController;
import systems.controllers.HuntingController;
import entities.grounds.Ground;
import entities.grounds.Water;
import entities.mobs.Mob;
import entities.mobs.Pig;
import entities.passives.Passive;
import systems.spawning.MobSpawner;
import generator.WorldLoader;
import graphics.GraphicsContext;
import graphics.GraphicsContextImpl;
import graphics.DrawablePresenter;
import graphics.ResolutionProvider;
import openable.OpenableManager;
import systems.*;
import systems.spawning.PlacementRules;
import systems.spawning.MobControlSystem;
import utils.*;

import java.io.IOException;
import java.util.*;

public class GameScreen implements Screen {
    private final WorldLoader loader;
    private final SpriteBatch spriteBatch = new SpriteBatch();
    private final AssetManager assetManager = new AssetManager("assets");
    private RenderingSystem renderingSystem;
    private InputSystem inputSystem;
    private OpenableManager openableManager;

    private ChunkManagerSystem chunkManagerSystem;
    private final MobControlSystem mobControlSystem;
    private MoveSystem moveSystem;
    private ActionManagerSystem actionManagerSystem;
    private UpdateSystem updateSystem;

    private Player player;
    private final Map<Pair<Integer,Integer>, Ground> ground;
    private final Map<Pair<Integer,Integer>, Passive> passives;
    private final Collection<Mob> mobs;

    public GameScreen(MudlandsGame mudlandsGame) {
        UniversalFactory universalFactory = new UniversalFactory(
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

        Random random = new Random(42);

        chunkManagerSystem = new ChunkManagerSystem(player,loader, ground,passives,mobs);

        Collection<Ground> groundsView = Collections.unmodifiableCollection(ground.values());
        Collection<Passive> passivesView = Collections.unmodifiableCollection(passives.values());
        Collection<Mob> mobsView = Collections.unmodifiableCollection(mobs);

        PlacementRules placementRules = new PlacementRules(
            Collections.unmodifiableMap(passives),
            Collections.unmodifiableMap(ground),
            mobsView
        );
        placementRules.forbidOn(Pig.class, Water.class);
        placementRules.forbidOn(Ghost.class, Water.class);

        HuntingController zombieHuntingController = new HuntingController(
            placementRules,
            player.mutablePositionComponent,
            30
        );
        zombieHuntingController.addHunter(Zombie.class);
        HuntingController ghostHuntingController = new HuntingController(
            placementRules,
            player.mutablePositionComponent,
            40
        );
        ghostHuntingController.addHunter(Ghost.class);

        MobSpawner spawner = new MobSpawner(placementRules, random, 5);
        mobControlSystem = new MobControlSystem(player.mutablePositionComponent, mobs, 40);
        mobControlSystem.addSpawningRule(8, spawner::spawnPigAround);
        mobControlSystem.addSpawningRule(10, spawner::spawnZombieAround);
        mobControlSystem.addSpawningRule(20, spawner::spawnGhostAround);
        mobControlSystem.registerController(Zombie.class, zombieHuntingController);
        mobControlSystem.registerController(Ghost.class, ghostHuntingController);
        mobControlSystem.registerController(Pig.class, new CluelessController(random, 100));

        moveSystem = new MoveSystem(placementRules, Collections.unmodifiableMap(ground), mobsView);

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
        mobControlSystem.update(delta);
        chunkManagerSystem.update();
        inputSystem.update(player, delta);
        actionManagerSystem.update(player,passives,mobs);
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
