import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.ScreenUtils;

public class MudlandsGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    } //change to MainMenuScreen latah bruda

    @Override
    public void render() {
        ScreenUtils.clear(Config.CLEAR_COLOR);
        super.render();
    }
}
