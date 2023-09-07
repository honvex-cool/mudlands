import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static utils.Config.UISKIN;

public class MainMenuScreen implements Screen {
    GdxGame gdxGame;
    MudlandsGame mudlandsGame;
    Stage stage;
    public MainMenuScreen(MudlandsGame mudlandsGame, GdxGame gdxGame) {
        this.mudlandsGame = mudlandsGame;
        this.gdxGame = gdxGame;
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal(UISKIN));
        TextButton playButton = new TextButton("PLAY", skin);
        TextButton loadWorldButton = new TextButton("LOAD", skin);
        TextButton uselessButton = new TextButton("USELESS", skin);
        TextButton exitButton = new TextButton("EXIT", skin);


        stage.addActor(playButton);
        stage.addActor(loadWorldButton);
        stage.addActor(uselessButton);
        stage.addActor(exitButton);
    }
    @Override
    public void show() {
        gdxGame.setScreen(new GameScreen(mudlandsGame, gdxGame));
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
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
