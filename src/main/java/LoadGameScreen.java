import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import entities.Player;


import static utils.Config.UISKIN;
import static utils.Config.WORLD_NAME;

public class LoadGameScreen implements Screen {
    private final MudlandsGame mudlandsGame;
    private final GdxGame gdxGame;
    private final Stage stage;

    Texture texture = new Texture(Gdx.files.internal("assets/image.png"));
    private final SpriteBatch batch;

    public LoadGameScreen(MudlandsGame mudlandsGame, GdxGame gdxGame) {
        this.mudlandsGame = mudlandsGame;
        this.gdxGame = gdxGame;
        stage = new Stage();
        batch = new SpriteBatch();
        Skin skin = new Skin(Gdx.files.internal(UISKIN));
        Table table = new Table();
        table.defaults().size(200f, 75f);
        table.setFillParent(true);

        TextField worldName = new TextField(WORLD_NAME, skin);

        TextButton loadButton = new TextButton("Load", skin);
        loadButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    mudlandsGame.load(worldName.getText());
                }
                catch(Exception e){
                    return true;
                }
                gdxGame.setUpPlayer(mudlandsGame.getPlayer());
                gdxGame.setScreen(new GameScreen(mudlandsGame, gdxGame));
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

        Gdx.input.setInputProcessor(stage);
        table.add(loadButton).pad(10f).row();
        table.add(worldName).pad(10f).row();
        table.add(goBackButton).pad(10f);
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
        stage.dispose();
        batch.dispose();
        texture.dispose();
    }
}
