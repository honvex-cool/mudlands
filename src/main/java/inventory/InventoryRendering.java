package inventory;

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

import static utils.Config.*;

public class InventoryRendering {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private boolean inventoryOpen;

    private Table inventoryTable;

    private boolean destroying;

    private Table mainTable;

    private Skin skin;
    private Stage stage;

    private Label objectLabel, edible, equippable, number;
    private TextButton useButton, destroyButton, equipButton;

    private final Inventory inventory = new Inventory();

    private int lastClickedI = -1;
    private int lastClickedJ = -1;

    public InventoryRendering() {
        skin = new Skin(Gdx.files.internal(UISKIN));
        spriteBatch = new SpriteBatch();

        stage = new Stage();


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
        leftTable.add(destroyButton).row();
        destroyButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                handleDestroyButton();
                return true;
            }
        });
        leftTable.add(equipButton).row();


        mainTable.add(leftTable).expand().fill().width(20f);
        mainTable.pad(20f);


        inventoryTable = new Table();
        for(int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                ImageButton inventorySlot = createInventorySlot(inventory.get(row, col).getObjectType());
                inventoryTable.add(inventorySlot).size(64).pad(5);
                int finalRow = row;
                int finalCol = col;
                inventorySlot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(destroying){
                            inventory.removeObject(finalRow, finalCol);
                            Texture downTexture = new Texture("assets/textures/MUD.png");
                            ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
                            style.up = new TextureRegionDrawable(downTexture);
                            style.down = new TextureRegionDrawable(downTexture);
                            inventorySlot.setStyle(style);
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

    public void oneTick() {
        if(inventoryOpen) {
            if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                inventoryOpen = !inventoryOpen;
                Gdx.input.setInputProcessor(null);
                lastClickedI = -1;
                lastClickedJ = 1;
            }
            this.update();
        }
        if(!inventoryOpen) {
            if(Gdx.input.isKeyPressed(Input.Keys.E)) {
                inventoryOpen = !inventoryOpen;
                Gdx.input.setInputProcessor(stage);
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
            objectLabel.setText("Name: " + inventory.get(lastClickedI, lastClickedJ).getObjectType().name());
            edible.setText("Edible: " + inventory.get(lastClickedI, lastClickedJ).isEdible());
            equippable.setText("Can Equip: " + inventory.get(lastClickedI, lastClickedJ).isEquippable());
            number.setText("Number: " + inventory.get(lastClickedI, lastClickedJ).getNumber());
        }
        else {
            objectLabel.setText("Name: ");
            edible.setText("Edible: ");
            equippable.setText("Can Equip: ");
            number.setText("Number: ");
        }
        stage.act();
        stage.draw();
    }

    private ImageButton createInventorySlot(InventoryFieldType fieldType) {
        Texture upTexture;
        if(fieldType == InventoryFieldType.NONE){
            upTexture = new Texture("assets/textures/MUD.png");
        }
        else{
            upTexture = new Texture("assets/inventory/" + fieldType.name() + ".png");
        }
        Texture downTexture = new Texture("assets/textures/MUD.png");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        ImageButton imageButton = new ImageButton(style);
        return imageButton;
    }


    private void handleClick(int row, int col) {
        System.out.println("button " + row + " " + col);
        lastClickedI = row;
        lastClickedJ = col;
    }

    private void handleDestroyButton(){
        destroying = !destroying;
    }
}
