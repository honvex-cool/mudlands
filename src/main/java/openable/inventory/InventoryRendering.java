package openable.inventory;

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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import entities.Player;
import openable.items.food.AppleItem;
import openable.items.Item;
import utils.AssetManager;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static utils.Config.*;

public class InventoryRendering {

    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private boolean dragging = false;
    private Table inventoryTable;

    private boolean movingItem = false;

    private Table mainTable;

    private Skin skin;

    public Stage getStage() {
        return stage;
    }

    private Stage stage;

    private Label objectLabel, edible, equippable, number;
    private TextButton useButton, destroyButton, equipButton;

    private Inventory inventory;

    private int lastClickedI = -1;
    private int lastClickedJ = -1;

    private AssetManager assetManager;

    private Map<Pair<Integer, Integer>, InventoryImage> InventoryImageMap = new HashMap<>();

    Dialog equip;

    private float offsetX, offsetY;

    Image image;

    public InventoryRendering(Player player, AssetManager assetManager) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.player = player;
        this.assetManager = assetManager;
        inventory = player.getInventory();


        mainTable = new Table();
        mainTable.setFillParent(true);

        Table leftTable = new Table();

        objectLabel = new Label("Name: ", skin);
        edible = new Label("Edible: ", skin);
        equippable = new Label("Can Equip: ", skin);
        number = new Label("Number: ", skin);

        leftTable.add(objectLabel).row();
        leftTable.add(edible).row();
        leftTable.add(equippable).row();
        leftTable.add(number).row();

        useButton = new TextButton("USE", skin);
        destroyButton = new TextButton("DESTROY", skin);
        equipButton = new TextButton("EQUIP", skin);
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
                movingItem = false;
                return true;
            }
        });
        leftTable.add(equipButton).row();

        equipButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                handleEquipButton();
                return true;
            }
        });

        mainTable.add(leftTable).expand().fill().width(20f);
        mainTable.pad(20f);

        inventoryTable = new Table();
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                InventoryImage inventorySlot = createInventorySlot(inventory.get(row, col).getItem());
                inventorySlot.i = row;
                inventorySlot.j = col;
                inventoryTable.add(inventorySlot).size(64).pad(5);
                InventoryImageMap.put(new Pair<>(row, col), inventorySlot);
                int finalRow = row;
                int finalCol = col;
                inventorySlot.addListener(new InputListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if(dragging) {
                            Actor hitActor = event.getStage().hit(Gdx.input.getX(), Gdx.input.getY(), true);
                            if(hitActor instanceof InventoryImage) {
                                InventoryImage inventoryImage = (InventoryImage)hitActor;
                                InventoryField tmp = new InventoryField();
                                InventoryField source = inventory.get(finalRow, finalCol);
                                InventoryField target = inventory.get(INVENTORY_HEIGHT - inventoryImage.i - 1, inventoryImage.j);
                                tmp.setField(source);
                                source.setField(target);
                                target.setField(tmp);
                                updateInventory();
                            }
                            image.remove();
                        }
                        dragging = false;
                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        if(dragging) {
                            float deltaX = x - offsetX;
                            float deltaY = y - offsetY;

                            image.moveBy(deltaX, deltaY);

                            offsetX = x;
                            offsetY = y;
                        }
                    }

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(dragging) {
                            return false;
                        }
                        image = new Image(assetManager.getInventoryTexture(inventory.get(finalRow, finalCol).getItem().toString()));
                        image.setSize(64f, 64f);
                        image.setPosition(Gdx.input.getX() - image.getWidth() / 2f, Gdx.graphics.getHeight() - Gdx.input.getY() - image.getHeight() / 2f);
                        stage.addActor(image);

                        offsetX = x;
                        offsetY = y;
                        dragging = true;
                        handleClick(finalRow, finalCol);
                        return true;
                    }
                });

            }
            inventoryTable.row();
        }
        mainTable.add(inventoryTable).expand().fill().width(80f);
        stage.addActor(mainTable);
        inventory.addItem(new AppleItem(), 2);


        equip = new Dialog("EQUIPPED", skin);
        equip.button("OK", true);
    }

    public void updateInventory() {
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                Pair<Integer, Integer> pair = new Pair<>(row, col);
                addTexture(inventory.get(row, col).getItem(), InventoryImageMap.get(pair));
            }
        }
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
            objectLabel.setText("Name: " + inventory.get(lastClickedI, lastClickedJ).getItem());
            edible.setText("Edible: " + inventory.get(lastClickedI, lastClickedJ).getItem().isEdible());
            equippable.setText("Can Equip: " + inventory.get(lastClickedI, lastClickedJ).getItem().isEquipable());
            number.setText("Number: " + inventory.get(lastClickedI, lastClickedJ).getNumber());
        } else {
            objectLabel.setText("Name: ");
            edible.setText("Edible: ");
            equippable.setText("Can Equip: ");
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


    private void handleClick(int row, int col) {
        lastClickedI = row;
        lastClickedJ = col;
    }

    private void handleEquipButton() {
        if(lastClickedI == -1) {
            return;
        }
        Item item = inventory.get(lastClickedI, lastClickedJ).getItem();
        if(item.isEquipable()) {
            inventory.equipItem(lastClickedI, lastClickedJ);
            player.setAttackDamage(item.getAttackStrength());
            updateInventory();
            equip.getContentTable().clearChildren();
            equip.show(stage);
        }
    }

    private void handleUseButton() {
        if(lastClickedI == -1) {
            return;
        }
        Item item = inventory.get(lastClickedI, lastClickedJ).getItem();
        if(item.isUsable()) {
            item.use(player);
            inventory.removeItem(lastClickedI, lastClickedJ, 1);
            updateInventory();
        }
    }

    private void handleDestroyButton() {
        if(lastClickedI == -1) {
            return;
        }
        inventory.removeItem(lastClickedI, lastClickedJ);
        Texture downTexture = assetManager.getInventoryTexture("None");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Pair<Integer, Integer> coords = new Pair<>(lastClickedI, lastClickedJ);
        style.up = new TextureRegionDrawable(downTexture);
        style.down = new TextureRegionDrawable(downTexture);
        InventoryImageMap.get(coords).setStyle(style);
    }
}
