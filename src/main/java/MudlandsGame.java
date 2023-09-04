import entities.EntityMappings;
import entities.Player;
import entities.UniversalFactory;
import entities.grounds.Ground;
import entities.grounds.Water;
import entities.mobs.*;
import entities.passives.Passive;
import generator.WorldLoader;
import graphics.DrawablePresenter;
import graphics.GraphicsContext;
import graphics.GraphicsContextImpl;
import graphics.GraphicsContextInventory;
import openable.crafting.CraftingManager;
import openable.inventory.InventoryManager;
import openable.status.StatusManager;
import systems.*;
import systems.controllers.CluelessController;
import systems.controllers.HuntingController;
import systems.spawning.MobControlSystem;
import systems.spawning.MobSpawner;
import systems.spawning.PlacementRules;
import utils.AssetManager;
import utils.Debug;
import utils.Pair;

import java.io.IOException;
import java.util.*;

public class MudlandsGame {
    private final WorldLoader loader;
    private GraphicsContext graphicsContext;
    private GraphicsContextInventory graphicsContextInventory;
    private final AssetManager assetManager = new AssetManager("assets");
    private StatusManager statusManager;
    private CraftingManager craftingManager;
    private InventoryManager inventoryManager;
    private RenderingSystem renderingSystem;
    private OpenableRenderingSystem openableRenderingSystem;
    private final InputSystem inputSystem;
    private ChunkManagerSystem chunkManagerSystem;
    private MobControlSystem mobControlSystem;
    private MoveSystem moveSystem;
    private final ActionManagerSystem actionManagerSystem;
    private UpdateSystem updateSystem;
    private Player player;
    private final Map<Pair<Integer,Integer>, Ground> ground;
    private final Map<Pair<Integer,Integer>, Passive> passives;
    private final Collection<Mob> mobs;

    private final Collection<Ground> groundsView;
    private final Collection<Passive> passivesView;
    private final Collection<Mob> mobsView;
    private boolean running = true;
    public MudlandsGame(){
        UniversalFactory universalFactory = new UniversalFactory(
            EntityMappings.GROUND_MAP,
            EntityMappings.PASSIVE_MAP
        );
        loader = new WorldLoader(universalFactory);

        inputSystem = new InputSystem();

        actionManagerSystem = new ActionManagerSystem();

        ground = new HashMap<>();
        passives = new HashMap<>();
        mobs = new ArrayList<>();

        groundsView = Collections.unmodifiableCollection(ground.values());
        passivesView = Collections.unmodifiableCollection(passives.values());
        mobsView = Collections.unmodifiableCollection(mobs);

        if(Debug.LOAD_WORLD) {
            load("testWorld");
        } else {
            create(42,"testWorld");
        }

    }
    public void update(float delta){
        updateSystem.update(delta);
        mobControlSystem.update(delta);
        chunkManagerSystem.update();
        inputSystem.update(player, delta);
        actionManagerSystem.update(player,passives,mobs);
        mobs.add(player);
        moveSystem.move(delta);
        mobs.remove(player);
        renderingSystem.update();
        openableRenderingSystem.update();

        if(player.isDestroyed()){
            running = false;
        }
    }

    public void create(long seed,String name){
        loader.createWorld(seed, name);
        player = loader.loadPlayer();
        prepare();
    }

    public void load(String name){
        try {
            loader.loadWorld(name);
            player = loader.loadPlayer();
        } catch(IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        prepare();
    }

    public void setGraphicsContext(GraphicsContextImpl graphicsContext, GraphicsContextInventory graphicsContextInventory){
        this.graphicsContext = graphicsContext;
        renderingSystem.setGraphicsContext(graphicsContext);
        this.graphicsContextInventory = graphicsContextInventory;
        openableRenderingSystem.setGraphicsContext(graphicsContextInventory);
    }

    private void prepare(){

        PlacementRules placementRules = new PlacementRules(
            Collections.unmodifiableMap(passives),
            Collections.unmodifiableMap(ground),
            mobsView
        );
        placementRules.forbidOn(Pig.class, Water.class);
        placementRules.forbidOn(Cow.class, Water.class);
        placementRules.forbidOn(Zombie.class, Water.class);

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

        Random randomForMobs = new Random(42);

        MobSpawner spawner = new MobSpawner(placementRules, randomForMobs, 5);
        mobControlSystem = new MobControlSystem(player.mutablePositionComponent, mobs, 40);
        mobControlSystem.addSpawningRule(20, spawner::spawnPigAround);
        mobControlSystem.addSpawningRule(10, spawner::spawnCowAround);
        mobControlSystem.addSpawningRule(30, spawner::spawnZombieAround);
        mobControlSystem.addSpawningRule(40, spawner::spawnGhostAround);
        mobControlSystem.registerController(Zombie.class, zombieHuntingController);
        mobControlSystem.registerController(Ghost.class, ghostHuntingController);
        mobControlSystem.registerController(Pig.class, new CluelessController(randomForMobs, 100));

        moveSystem = new MoveSystem(placementRules, Collections.unmodifiableMap(ground), mobsView);

        chunkManagerSystem = new ChunkManagerSystem(player,loader, ground,passives,mobs);

        updateSystem = new UpdateSystem(
            player,
            groundsView,
            passivesView,
            mobsView
        );

        renderingSystem = new RenderingSystem(
            new DrawablePresenter(),
            player,
            groundsView,
            passivesView,
            mobsView
        );

        craftingManager = new CraftingManager(player.getInventory());
        statusManager = new StatusManager(player);
        inventoryManager = new InventoryManager(player);
        openableRenderingSystem = new OpenableRenderingSystem(inputSystem);
    }
    public InventoryManager getInventoryManager(){
        return inventoryManager;
    }
    public StatusManager getStatusManager() {
        return statusManager;
    }

    public CraftingManager getCraftingManager() {
        return craftingManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void dispose(){
        assetManager.dispose();
        chunkManagerSystem.unloadAll();
        loader.savePlayer(player);
        try {
            loader.saveWorld();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isRunning(){
        return running;
    }
}
