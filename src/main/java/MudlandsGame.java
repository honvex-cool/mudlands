import utils.Config;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class MudlandsGame extends Game {

    GameScreen screen;

    @Override
    public void create() {
        screen = new GameScreen(this);
        setScreen(screen);
    } //change to MainMenuScreen latah bruda

    @Override
    public void render() {
        ScreenUtils.clear(Config.CLEAR_COLOR);
        super.render();
    }

    @Override
    public void dispose() {
        screen.dispose();
        super.dispose();
    }
}
