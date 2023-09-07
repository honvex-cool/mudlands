import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
    private final MudlandsGame mudlandsGame;
    private final GdxGame gdxGame;
    public GameScreen(MudlandsGame mudlandsGame,GdxGame gdxGame) {
        this.mudlandsGame = mudlandsGame;
        this.gdxGame = gdxGame;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mudlandsGame.update(delta);
        if(!mudlandsGame.isRunning()){
            mudlandsGame.dispose();
            gdxGame.setScreen(new MainMenuScreen(mudlandsGame, gdxGame));
        }

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
