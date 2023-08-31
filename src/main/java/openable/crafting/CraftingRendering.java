package openable.crafting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private int page;

    private int maxPage = 0;
    private AssetManager assetManager;

    private ArrayList<Page> pages;

    CraftedPopup popupAccept;
    CraftedPopup popupDeny;

    public CraftingRendering(Player player, AssetManager assetManager) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.inventory = player.getInventory();
        this.assetManager = assetManager;
        page = 0;
        mainTable = new Table();
        inventoryTable = new Table();
        mainTable.setFillParent(true);

        pages = new ArrayList<>();
        createPages();

        TextButton leftButton = new TextButton("LEFT", skin);
        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                page--;
                updatePage();
                return true;
            }
        });
        TextButton rightButton = new TextButton("RIGHT", skin);
        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                page++;
                updatePage();
                return true;
            }
        });

        popupAccept = new CraftedPopup("", skin);
        popupAccept.text("");
        popupAccept.button("OK", true);

        popupDeny = new CraftedPopup("MUD!", skin);
        popupDeny.text("Not enough materials");
        popupDeny.button("OK", true);

        showPage(pages.get(page));
        mainTable.add(leftButton);
        mainTable.add(inventoryTable);
        mainTable.add(rightButton);
        stage.addActor(mainTable);

    }


    private void showPage(Page page) {
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
                        if(!item.isCraftable()) {
                            popupDeny.getContentTable().clearChildren();
                            popupDeny.text("Cannot be crafted");
                            popupDeny.show(stage);
                        }
                        else if(item.craft(inventory)) {
                            popupAccept.getContentTable().clearChildren();
                            popupAccept.text("Crafted " + item);
                            popupAccept.show(stage);
                        } else {
                            popupDeny.getContentTable().clearChildren();
                            popupDeny.text("Not enough materials: " + item.getRecipe());
                            popupDeny.show(stage);
                        }
                        return true;
                    }
                });
            }
            inventoryTable.row();
        }
    }

    private void createPages() {
        Page page = new Page("Tools");
        page.addItem(new PickaxeItem());
        page.addItem(new SwordItem());
        page.addItem(new AxeItem());
        pages.add(page);
    }

    private void updatePage() {
        if(page < 0) {
            page = 0;
            return;
        }
        if(page > maxPage) {
            page = maxPage;
            return;
        }
        //nextPageOfCraftableItems
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
