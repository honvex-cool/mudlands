package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import openable.inventory.Inventory;
import openable.inventory.InventoryImage;
import openable.inventory.InventoryManager;
import openable.items.Item;
import utils.AssetManager;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static utils.Config.*;

public class InventoryRendering {

    InventoryManager inventoryManager;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    boolean dragging = false;
    private final Skin skin;
    private final Dialog description;
    public Stage getStage() {
        return stage;
    }
    private final Stage stage;
    private final Label objectLabel;
    private final Label edible;
    private final Label number;

    int lastClickedI = -1;
    int lastClickedJ = -1;
    AssetManager assetManager;
    private final Map<Pair<Integer, Integer>, InventoryImage> inventoryImageMap = new HashMap<>();
    float offsetX;
    float offsetY;
    Image image;
    private final Table equipmentTable;

    public InventoryRendering(InventoryManager inventoryManager, AssetManager assetManager) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        stage = new Stage();
        this.inventoryManager = inventoryManager;
        this.assetManager = assetManager;

        description = new Dialog("description", skin);
        description.button("OK");

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        equipmentTable = new Table();

        setUpEquipment(inventoryManager.getHead(), 0, INVENTORY_WIDTH);
        setUpEquipment(inventoryManager.getChest(), 1, INVENTORY_WIDTH);
        setUpEquipment(inventoryManager.getLegs(), 2, INVENTORY_WIDTH);
        setUpEquipment(inventoryManager.getBoots(), 3, INVENTORY_WIDTH);
        setUpEquipment(inventoryManager.getRightHand(), 4, INVENTORY_WIDTH);
        setUpEquipment(inventoryManager.getLeftHand(), 5, INVENTORY_WIDTH);

        mainTable.add(equipmentTable).left();

        Table leftTable = new Table();

        objectLabel = new Label("Name: ", skin);
        edible = new Label("Edible: ", skin);
        number = new Label("Number: ", skin);

        leftTable.add(objectLabel).row();
        leftTable.add(edible).row();
        leftTable.add(number).row();

        TextButton useButton = new TextButton("USE", skin);
        TextButton destroyButton = new TextButton("DESTROY", skin);
        leftTable.defaults().size(200f, 50f);

        leftTable.add(useButton).row();
        useButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                handleUseButton();
                return true;
            }
        });

        leftTable.add(destroyButton).row();
        destroyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                handleDestroyButton();
                return true;
            }
        });

        mainTable.add(leftTable).expand().fill().width(20f);
        mainTable.pad(80f);

        Table inventoryTable = new Table();
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                InventoryImage inventorySlot = createInventorySlot(inventoryManager.getItem(row, col));
                inventorySlot.i = row;
                inventorySlot.j = col;
                inventoryTable.add(inventorySlot).size(64).pad(5);
                inventoryImageMap.put(new Pair<>(row, col), inventorySlot);
                inventorySlot.addListener(new InventoryChangeListener(this, row, col));

            }
            inventoryTable.row();
        }
        mainTable.add(inventoryTable).expand().fill().width(80f);
        stage.addActor(mainTable);
    }

    public void updateInventory() {
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col <= INVENTORY_WIDTH; col++) {
                Pair<Integer, Integer> pair = new Pair<>(row, col);
                addTexture(inventoryManager.getItem(row, col), inventoryImageMap.get(pair));
            }
        }
    }


    public void setUpEquipment(Item item, int i, int j) {
        InventoryImage equipment = createInventorySlot(item);
        equipment.set(i, j);
        equipment.addListener(new InventoryChangeListener(this, i, j));
        inventoryImageMap.put(new Pair<>(i, j), equipment);
        equipmentTable.add(equipment).size(64f).pad(5f).row();
    }

    public void update() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0f, 0f, 0f, 0.5f));
        shapeRenderer.rect(10f, 10f, Gdx.graphics.getWidth() - 20f, Gdx.graphics.getHeight() - 20f);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if(lastClickedI != -1) {
            objectLabel.setText("Name: " + inventoryManager.getItem(lastClickedI, lastClickedJ));
            edible.setText("Edible: " + inventoryManager.getItem(lastClickedI, lastClickedJ).isEdible());
            number.setText("Number: " + inventoryManager.get(lastClickedI, lastClickedJ).getNumber());
        } else {
            objectLabel.setText("Name: ");
            edible.setText("Edible: ");
            number.setText("Number: ");
        }
        stage.act();
        stage.draw();
    }

    private void addTexture(Item item, InventoryImage image) {
        Texture upTexture = assetManager.getInventoryTexture(item.toString());
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Texture downTexture = assetManager.getInventoryTexture("None");
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        image.setStyle(style);
    }

    private InventoryImage createInventorySlot(Item item) {
        Texture upTexture = assetManager.getInventoryTexture(item.toString());
        Texture downTexture = assetManager.getInventoryTexture("None");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        return new InventoryImage(style);
    }


    void handleClick(int row, int col) {
        lastClickedI = row;
        lastClickedJ = col;
    }

    private void handleUseButton() {
        if(lastClickedI == -1) {
            return;
        }
        Item item = inventoryManager.getItem(lastClickedI, lastClickedJ);
        if(item.isEdible()) {
            item.use(inventoryManager.getPlayer());
            inventoryManager.removeItem(lastClickedI, lastClickedJ, 1);
            updateInventory();
        }
    }

    private void handleDestroyButton() {
        if(lastClickedI == -1) {
            return;
        }
        inventoryManager.removeItem(lastClickedI, lastClickedJ);
        Texture downTexture = assetManager.getInventoryTexture("None");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Pair<Integer, Integer> coords = new Pair<>(lastClickedI, lastClickedJ);
        style.up = new TextureRegionDrawable(downTexture);
        style.down = new TextureRegionDrawable(downTexture);
        inventoryImageMap.get(coords).setStyle(style);
    }

    public void showDescription(int i, int j){
        description.getContentTable().clearChildren();
        description.text(inventoryManager.getItem(i, j).getRecipe());
        description.text(inventoryManager.getItem(i, j).getStats());
        description.show(stage);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
    }

    public Inventory getInventory() {
        return inventoryManager.getPlayer().getInventory();
    }
}
