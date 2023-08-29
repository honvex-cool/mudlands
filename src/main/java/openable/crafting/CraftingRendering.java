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
import openable.items.PickaxeItem;

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

    private int maxPage = 1;

    public CraftingRendering(Player player) {
        skin = new Skin(Gdx.files.internal(UISKIN));
        this.stage = new Stage();
        this.inventory = player.getInventory();
        page = 1;
        mainTable = new Table();
        inventoryTable = new Table();
        mainTable.setFillParent(true);

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

        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                Item tmp = new PickaxeItem();
                ImageButton inventorySlot = createInventorySlot(tmp);
                inventoryTable.add(inventorySlot).size(64).pad(5);
                inventorySlot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        tmp.craft(inventory);
                        return true;
                    }
                });
            }
            inventoryTable.row();
        }
        mainTable.add(leftButton);
        mainTable.add(inventoryTable);
        mainTable.add(rightButton);
        stage.addActor(mainTable);
    }

    private void updatePage() {
        if(page == 0){
            page = 1;
            return;
        }
        if(page > maxPage){
            page = maxPage;
            return;
        }
        //nextPageOfCraftableItems
    }

    private ImageButton createInventorySlot(Item item) {
        Texture upTexture = new Texture("assets/inventory/" + item + ".png");
        Texture downTexture = new Texture("assets/inventory/None.png");
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
