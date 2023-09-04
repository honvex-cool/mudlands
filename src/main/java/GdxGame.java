import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphics.GraphicsContextImpl;
import graphics.GraphicsContextInventoryImpl;
import graphics.ResolutionProvider;
import utils.Config;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class GdxGame extends Game {

    GameScreen gameScreen;
    GameScreen mainMenuScreen;
    private GraphicsContextImpl graphicsContext;

    private GraphicsContextInventoryImpl graphicsContextInventory;
    private final MudlandsGame mudlandsGame;

    public GdxGame(MudlandsGame mudlandsGame){
        super();
        this.mudlandsGame = mudlandsGame;
    }
    @Override
    public void create() {
        graphicsContext = new GraphicsContextImpl(new SpriteBatch(),new OrthographicCamera(),new ResolutionProvider(), Config.TILES_ON_SCREEN);
        graphicsContextInventory = new GraphicsContextInventoryImpl(mudlandsGame.getInventoryManager(), mudlandsGame.getCraftingManager(), mudlandsGame.getStatusManager(), mudlandsGame.getAssetManager());
        mudlandsGame.setGraphicsContext(graphicsContext, graphicsContextInventory);

        gameScreen = new GameScreen(mudlandsGame,this);
        setScreen(gameScreen);
    } //change to MainMenuScreen latah bruda

    @Override
    public void render() {
        ScreenUtils.clear(Config.CLEAR_COLOR);
        super.render();
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        graphicsContext.dispose();
        graphicsContextInventory.dispose();
        super.dispose();
    }
}
