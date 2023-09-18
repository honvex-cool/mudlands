import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static utils.Config.UISKIN;

public class MainMenuScreen implements Screen {
    GdxGame gdxGame;
    MudlandsGame mudlandsGame;
    Stage stage;

    Texture texture = new Texture(Gdx.files.internal("assets/image.png"));
    private final SpriteBatch batch;

    private final Dialog infoDialog;
    public MainMenuScreen(MudlandsGame mudlandsGame, GdxGame gdxGame) {
        this.mudlandsGame = mudlandsGame;
        this.gdxGame = gdxGame;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        Skin skin = new Skin(Gdx.files.internal(UISKIN));
        TextButton playButton = new TextButton("PLAY", skin);
        TextButton loadButton = new TextButton("LOAD", skin);
        TextButton infoButton = new TextButton("INFO", skin);
        TextButton exitButton = new TextButton("EXIT", skin);

        infoDialog = new Dialog("Authors", skin);
        infoDialog.button("OK");
        infoDialog.text("Filip Jasioniowicz \n Jakub Oskwarek \n Jakub Was");



        playButton.setSize(200f, 100f);
        playButton.setPosition(1.5f * (Gdx.graphics.getWidth() - playButton.getWidth())/5, 3.5f *(Gdx.graphics.getHeight() - playButton.getHeight())/5);
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gdxGame.setScreen(new CreateGameScreen(mudlandsGame, gdxGame));
                return true;
            }
        });

        loadButton.setSize(200f, 100f);
        loadButton.setPosition(3.5f * (Gdx.graphics.getWidth() - loadButton.getWidth())/5, 3.5f*(Gdx.graphics.getHeight() - loadButton.getHeight())/5);
        loadButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gdxGame.setScreen(new LoadGameScreen(mudlandsGame, gdxGame));
                return true;
            }
        });

        infoButton.setSize(200f, 100f);
        infoButton.setPosition(1.5f*(Gdx.graphics.getWidth() - infoButton.getWidth())/5, 1.5f*(Gdx.graphics.getHeight() - infoButton.getHeight())/5);
        infoButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showInfoDialog();
                return true;
            }
        });

        exitButton.setSize(200f, 100f);
        exitButton.setPosition(3.5f*(Gdx.graphics.getWidth() - exitButton.getWidth())/5, 1.5f*(Gdx.graphics.getHeight() - exitButton.getHeight())/5);
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        stage.addActor(playButton);
        stage.addActor(loadButton);
        stage.addActor(infoButton);
        stage.addActor(exitButton);
    }

    private void showInfoDialog() {
        infoDialog.show(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
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
        batch.dispose();
        texture.dispose();
        stage.dispose();
    }
}
