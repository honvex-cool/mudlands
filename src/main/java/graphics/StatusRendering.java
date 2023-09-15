package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import openable.inventory.Inventory;
import openable.status.StatusManager;
import utils.AssetManager;

import static utils.Config.UISKIN;

public class StatusRendering {

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final Skin skin;
    private final Stage stage;
    private final AssetManager assetManager;
    private StatusManager statusManager;
    private Image headImage;
    private Image chestImage;
    private Image legsImage;
    private Image bootsImage;
    private Image rightHandImage;
    private Image leftHandImage;

    public StatusRendering(AssetManager assetManager) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        stage = new Stage();
        this.assetManager = assetManager;
    }

    public void setPlayer(Inventory inventory){
        stage.clear();
        this.statusManager = new StatusManager(inventory);

        headImage = new Image(assetManager.getInventoryTexture(statusManager.getHead().toString()));
        headImage.setSize(64, 64);
        chestImage = new Image(assetManager.getInventoryTexture(statusManager.getChest().toString()));
        chestImage.setSize(64, 64);
        legsImage = new Image(assetManager.getInventoryTexture(statusManager.getLegs().toString()));
        legsImage.setSize(64, 64);
        bootsImage = new Image(assetManager.getInventoryTexture(statusManager.getBoots().toString()));
        bootsImage.setSize(64, 64);
        rightHandImage = new Image(assetManager.getInventoryTexture(statusManager.getRightHand().toString()));
        rightHandImage.setSize(64, 64);
        leftHandImage = new Image(assetManager.getInventoryTexture(statusManager.getLeftHand().toString()));
        leftHandImage.setSize(64, 64);

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.defaults().size(64f);
        mainTable.add(rightHandImage).pad(25);
        Table equipmentTable = new Table();
        equipmentTable.defaults().size(64f);
        equipmentTable.add(headImage).pad(25).row();
        equipmentTable.add(chestImage).pad(25).row();
        equipmentTable.add(legsImage).pad(25).row();
        equipmentTable.add(bootsImage).pad(25).row();
        mainTable.add(equipmentTable);
        mainTable.add(leftHandImage).pad(25);

        stage.addActor(mainTable);
    }

    public void updateInventory() {
        statusManager.updateStatus();
        headImage.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(statusManager.getHead().toString())));
        chestImage.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(statusManager.getChest().toString())));
        legsImage.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(statusManager.getLegs().toString())));
        bootsImage.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(statusManager.getBoots().toString())));
        rightHandImage.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(statusManager.getRightHand().toString())));
        leftHandImage.setDrawable(new TextureRegionDrawable(assetManager.getInventoryTexture(statusManager.getLeftHand().toString())));
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

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
