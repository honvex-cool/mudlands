package openable.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import entities.Player;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

import static utils.Config.*;

public class InventoryRendering {

    private Player player;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    private Table inventoryTable;

    private boolean movingItem = false;

    private Table mainTable;

    private Skin skin;

    public Stage getStage() {
        return stage;
    }

    private Stage stage;

    private Label objectLabel, edible, equippable, number;
    private TextButton useButton, destroyButton, equipButton, moveButton;

    private Inventory inventory;

    public int getLastClickedI() {
        return lastClickedI;
    }

    public void setLastClickedI(int lastClickedI) {
        this.lastClickedI = lastClickedI;
    }

    public int getLastClickedJ() {
        return lastClickedJ;
    }

    public void setLastClickedJ(int lastClickedJ) {
        this.lastClickedJ = lastClickedJ;
    }

    private int lastClickedI = -1;
    private int lastClickedJ = -1;

    private int movingFromI = -1;

    private int movingFromJ = -1;

    private int movingToI = -1;

    private int movingToJ = -1;

    private Map<Pair<Integer, Integer>, ImageButton> imageButtonMap = new HashMap<>();

    public InventoryRendering(Player player) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.player = player;
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
        moveButton = new TextButton("MOVE", skin);
        leftTable.defaults().size(200f, 50f);

        leftTable.add(useButton).row();
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
        leftTable.add(moveButton).row();

        moveButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(lastClickedJ != -1) {
                    movingFromI = lastClickedI;
                    movingFromJ = lastClickedJ;
                    movingItem = true;
                }
                return true;
            }
        });


        mainTable.add(leftTable).expand().fill().width(20f);
        mainTable.pad(20f);


        inventoryTable = new Table();
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                ImageButton inventorySlot = createInventorySlot(inventory.get(row, col).getItemType());
                inventoryTable.add(inventorySlot).size(64).pad(5);
                imageButtonMap.put(new Pair<>(row, col), inventorySlot);
                int finalRow = row;
                int finalCol = col;
                inventorySlot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(movingItem) {
                            movingToI = finalRow;
                            movingToJ = finalCol;
                        }
                        handleClick(finalRow, finalCol);
                        return true;
                    }
                });
            }
            inventoryTable.row();
        }
        mainTable.add(inventoryTable).expand().fill().width(80f);
        stage.addActor(mainTable);
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
            objectLabel.setText("Name: " + inventory.get(lastClickedI, lastClickedJ).getItemType().name());
            edible.setText("Edible: " + inventory.get(lastClickedI, lastClickedJ).isEdible());
            equippable.setText("Can Equip: " + inventory.get(lastClickedI, lastClickedJ).isEquippable());
            number.setText("Number: " + inventory.get(lastClickedI, lastClickedJ).getNumber());
        } else {
            objectLabel.setText("Name: ");
            edible.setText("Edible: ");
            equippable.setText("Can Equip: ");
            number.setText("Number: ");
        }
        if(movingItem && movingToI != -1) {
            InventoryField tmp = new InventoryField();
            InventoryField from = inventory.get(movingFromI, movingFromJ);
            ImageButton fromImage = imageButtonMap.get(new Pair<>(movingFromI, movingFromJ));


            InventoryField to = inventory.get(movingToI, movingToJ);
            ImageButton toImage = imageButtonMap.get(new Pair<>(movingToI, movingToJ));
            tmp.setField(from);
            from.setField(to);
            to.setField(tmp);

            addTexture(from, fromImage);

            addTexture(to, toImage);

            movingItem = false;
            movingToI = -1;
            movingToJ = -1;
        }
        stage.act();
        stage.draw();
    }

    private void addTexture(InventoryField from, ImageButton fromImage) {
        Texture upTexture = new Texture("assets/inventory/" + from.getItemType().name() + ".png");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Texture downTexture = new Texture("assets/inventory/NONE.png");
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        fromImage.setStyle(style);
    }

    private ImageButton createInventorySlot(InventoryFieldType fieldType) {
        Texture upTexture = new Texture("assets/inventory/" + fieldType.name() + ".png");
        Texture downTexture = new Texture("assets/inventory/NONE.png");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        ImageButton imageButton = new ImageButton(style);
        return imageButton;
    }


    private void handleClick(int row, int col) {
        lastClickedI = row;
        lastClickedJ = col;
    }

    private void handleDestroyButton() {
        if(lastClickedI == -1) {
            return;
        }
        inventory.removeItem(lastClickedI, lastClickedJ);
        Texture downTexture = new Texture("assets/inventory/NONE.png");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Pair<Integer, Integer> coords = new Pair<>(lastClickedI, lastClickedJ);
        style.up = new TextureRegionDrawable(downTexture);
        style.down = new TextureRegionDrawable(downTexture);
        imageButtonMap.get(coords).setStyle(style);
    }
}
