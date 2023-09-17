import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.Random;

import static utils.Config.UISKIN;
import static utils.Config.WORLD_NAME;

public class CreateGameScreen implements Screen {
    private final MudlandsGame mudlandsGame;
    private final GdxGame gdxGame;
    private final Stage stage;

    Random random;

    Texture texture = new Texture(Gdx.files.internal("assets/image.png"));
    private final SpriteBatch batch;
    public CreateGameScreen(MudlandsGame mudlandsGame, GdxGame gdxGame) {
        random = new Random();
        this.mudlandsGame = mudlandsGame;
        this.gdxGame = gdxGame;
        batch = new SpriteBatch();
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal(UISKIN));
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().size(200f,75f);
        Gdx.input.setInputProcessor(stage);

        TextField seed = new TextField(String.valueOf(Math.abs(random.nextLong())), skin);

        TextField worldName = new TextField(WORLD_NAME, skin);

        TextButton playButton = new TextButton("Play", skin);

        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mudlandsGame.create(Long.parseLong(seed.getText()), worldName.getText());
                gdxGame.setUpPlayer(mudlandsGame.getPlayer());
                gdxGame.setScreen(new GameScreen(mudlandsGame,gdxGame));
                return true;
            }
        });

        TextButton goBackButton = new TextButton("Menu", skin);
        goBackButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gdxGame.setScreen(new MainMenuScreen(mudlandsGame, gdxGame));
                return true;
            }
        });

        table.add(playButton).pad(10f).row();
        table.add(seed).pad(10f).row();
        table.add(worldName).pad(10f).row();
        table.add(goBackButton).pad(10f).row();
        stage.addActor(table);
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
        stage.dispose();
        texture.dispose();
    }
}
