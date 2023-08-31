package openable.status;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import entities.Player;
import openable.inventory.Inventory;
import utils.AssetManager;

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

    private Texture noneTexture;

    private AssetManager assetManager;

    private Inventory inventory;

    private TextButton unEquipButton;
    public StatusRendering(Player player, AssetManager assetManager) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.player = player;
        this.inventory = player.getInventory();
        this.assetManager = assetManager;
        noneTexture = assetManager.getInventoryTexture("None");

        mainTable = new Table();
        mainTable.setFillParent(true);

        hp = new Label("HP: " + this.player.getHp().getCurrentPoints(), skin);
        unEquipButton = new TextButton("UNEQUIP", skin);
        image = new Image(noneTexture);

        mainTable.add(hp);
        mainTable.add(image).size(64f);
        mainTable.row();
        mainTable.add(unEquipButton);

        unEquipButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                inventory.unEquipItem();
                return true;
            }
        });

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
        hp.setText("HP: " + this.player.getHp().getCurrentPoints());
        image.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(inventory.getRightHand().toString())));
        stage.act();
        stage.draw();
    }
}
