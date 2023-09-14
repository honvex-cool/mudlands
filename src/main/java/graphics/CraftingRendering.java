package graphics;

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
import openable.crafting.CraftingManager;
import openable.crafting.Page;
import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.NoneItem;
import utils.AssetManager;

import java.util.ArrayList;

import static utils.Config.*;

public class CraftingRendering {

    private final ShapeRenderer shapeRenderer;

    public Stage getStage() {
        return stage;
    }

    private final Stage stage;
    private final Skin skin;

    private Table inventoryTable;

    private final AssetManager assetManager;

    Dialog popupAccept;
    Dialog popupDeny;

    Dialog popupInfo;

    private CraftingManager craftingManager;

    public CraftingRendering(AssetManager assetManager) {
        shapeRenderer = new ShapeRenderer();
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.assetManager = assetManager;

    }

    public void setPlayer(Player player){
        stage.clear();
        this.craftingManager = new CraftingManager(player.getInventory());
        Table mainTable = new Table();
        inventoryTable = new Table();
        mainTable.setFillParent(true);

        TextButton leftButton = new TextButton("LEFT", skin);
        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(CraftingRendering.this.craftingManager.updatePage(-1)) {
                    showPage(CraftingRendering.this.craftingManager.getCurrentPage());
                }
                return true;
            }
        });
        TextButton rightButton = new TextButton("RIGHT", skin);
        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(CraftingRendering.this.craftingManager.updatePage(1)) {
                    showPage(CraftingRendering.this.craftingManager.getCurrentPage());
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

        showPage(this.craftingManager.getCurrentPage());
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
                            popupInfo.getTitleLabel().setText(item.toString());
                            showPopup(popupInfo, item.getRecipe());
                            return true;
                        }
                        if(!item.isCraftable()) {
                            popupDeny.getTitleLabel().setText(item.toString());
                            showPopup(popupDeny, "Cannot be crafted");
                        } else if(craftingManager.craft(item)) {
                            popupAccept.getTitleLabel().setText(item.toString());
                            showPopup(popupAccept, "Crafted " + item);
                        } else {
                            popupDeny.getTitleLabel().setText(item.toString());
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
        return new ImageButton(style);
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

    public void dispose() {
        stage.dispose();
        skin.dispose();
        shapeRenderer.dispose();
    }
}
