package openable.crafting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import entities.Player;
import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.NoneItem;
import openable.items.tools.AxeItem;
import openable.items.tools.PickaxeItem;
import openable.items.tools.SwordItem;
import utils.AssetManager;

import java.util.ArrayList;

import static utils.Config.*;

public class CraftingRendering {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Stage getStage() {
        return stage;
    }

    private Stage stage;
    private Skin skin;

    private Inventory inventory;
    private Table mainTable;

    private Table inventoryTable;

    private AssetManager assetManager;

    private ArrayList<Page> pages;

    Dialog popupAccept;
    Dialog popupDeny;

    Dialog popupInfo;

    private CraftingManager manager;

    public CraftingRendering(Inventory inventory, AssetManager assetManager) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.inventory = inventory;
        this.assetManager = assetManager;
        this.manager = new CraftingManager();
        mainTable = new Table();
        inventoryTable = new Table();
        mainTable.setFillParent(true);

        TextButton leftButton = new TextButton("LEFT", skin);
        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(manager.updatePage(-1)) {
                    showPage(manager.getCurrentPage());
                }
                return true;
            }
        });
        TextButton rightButton = new TextButton("RIGHT", skin);
        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(manager.updatePage(1)) {
                    showPage(manager.getCurrentPage());
                }
                return true;
            }
        });

        popupAccept = new Dialog("", skin);
        popupAccept.text("");
        popupAccept.button("OK", true);

        popupDeny = new Dialog("MUD!", skin);
        popupDeny.text("Not enough materials");
        popupDeny.button("OK", true);

        popupInfo = new Dialog("Description", skin);
        popupInfo.text("");
        popupInfo.button("OK", true);

        showPage(manager.getCurrentPage());
        mainTable.add(leftButton);
        mainTable.add(inventoryTable);
        mainTable.add(rightButton);
        stage.addActor(mainTable);
    }


    private void showPage(Page page) {
        inventoryTable.clear();
        int counter = 0;
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                Item item;
                if(counter >= page.getSize()) {
                    item = new NoneItem();
                } else {
                    item = page.getItem(counter);
                }
                counter++;
                ImageButton inventorySlot = createInventorySlot(item);
                inventoryTable.add(inventorySlot).size(64).pad(5);
                inventorySlot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(item.toString().equals("None")){
                            return false;
                        }
                        if(button == Input.Buttons.RIGHT && item.isCraftable()) {
                            showPopup(popupInfo, item.getRecipe());
                            return true;
                        }
                        if(!item.isCraftable()) {
                            showPopup(popupDeny, "Cannot be crafted");
                        } else if(item.craft(inventory)) {
                            showPopup(popupAccept, "Crafted " + item);
                        } else {
                            showPopup(popupDeny, "Not enough materials: " + item.getRecipe());
                        }
                        return true;
                    }
                });
            }
            inventoryTable.row();
        }
    }

    private void showPopup(Dialog dialog, String text) {
        dialog.getContentTable().clearChildren();
        dialog.text(text);
        dialog.show(stage);
    }

    private ImageButton createInventorySlot(Item item) {
        Texture upTexture = assetManager.getInventoryTexture(item.toString());
        Texture downTexture = assetManager.getInventoryTexture("None");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        ImageButton imageButton = new ImageButton(style);
        return imageButton;
    }

    public void update() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(100f, 100f, 100f, 0.5f));
        shapeRenderer.rect(10f, 10f, Gdx.graphics.getWidth() - 20f, Gdx.graphics.getHeight() - 20f);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        stage.act();
        stage.draw();
    }
}
