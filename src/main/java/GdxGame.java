import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Player;
import graphics.*;
import openable.inventory.Inventory;
import utils.Config;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxGame extends Game {
    MainMenuScreen mainMenuScreen;
    private final MudlandsGame mudlandsGame;

    public GdxGame(MudlandsGame mudlandsGame){
        super();
        this.mudlandsGame = mudlandsGame;
    }

    GraphicsContextImpl graphicsContext;
    GraphicsContextInventory graphicsContextInventory;

    @Override
    public void create() {
        mainMenuScreen = new MainMenuScreen(mudlandsGame, this);
        graphicsContext = new GraphicsContextImpl(
            new SpriteBatch(),
            new OrthographicCamera(),
            new ResolutionProvider(),
            mudlandsGame.getAssetManager(),
            Config.TILES_ON_SCREEN
        );

        graphicsContextInventory = new GraphicsContextInventoryImpl(mudlandsGame.getAssetManager());
        setScreen(mainMenuScreen);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Config.CLEAR_COLOR);
        super.render();
    }

    @Override
    public void dispose() {
        mainMenuScreen.dispose();
        graphicsContext.dispose();
        graphicsContextInventory.dispose();
        super.dispose();
    }

    public void setUpPlayer(Player player) {
        graphicsContextInventory.setPlayer(player);
        mudlandsGame.setGraphicsContext(graphicsContext, graphicsContextInventory);
    }
}
