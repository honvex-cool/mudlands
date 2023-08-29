package openable.status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import entities.Player;

import static utils.Config.UISKIN;

public class StatusRendering {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Stage getStage() {
        return stage;
    }

    private Stage stage;

    private Skin skin;

    private Label hp;

    private Table mainTable;

    private Player player;

    private Image image;

    Texture noneTexture = new Texture("assets/inventory/NONE.png");

    public StatusRendering(Player player) {
        this.stage = new Stage();
        this.player = player;
        skin = new Skin(Gdx.files.internal(UISKIN));
        mainTable = new Table();
        mainTable.setFillParent(true);

        hp = new Label("HP: " + 100, skin);
        image = new Image(noneTexture);

        mainTable.add(hp);
        mainTable.add(image).size(64f);

        stage.addActor(mainTable);
    }


    public void update() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0f, 100f, 100f, 0.5f));
        shapeRenderer.rect(10f, 10f, Gdx.graphics.getWidth() - 20f, Gdx.graphics.getHeight() - 20f);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.act();
        stage.draw();
    }
}
