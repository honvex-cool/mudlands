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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import static utils.Config.*;

public class InventoryRendering {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private boolean inventoryOpen;

    private Table inventoryTable;

    private Table mainTable;

    private Skin skin;
    private Stage stage;

    public InventoryRendering() {
        spriteBatch = new SpriteBatch();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        mainTable = new Table();
        mainTable.setFillParent(true);


        inventoryTable = new Table();
        for (int row = 0; row < INVENTORY_HEIGHT; row++) {
            for(int col = 0; col < INVENTORY_WIDTH; col++) {
                ImageButton inventorySlot = createInventorySlot();
                inventoryTable.add(inventorySlot).size(64).pad(5);
                int finalRow = row;
                int finalCol = col;
                inventorySlot.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
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
            }
            this.update();
        }
        if(!inventoryOpen) {
            if(Gdx.input.isKeyPressed(Input.Keys.E)) {
                inventoryOpen = !inventoryOpen;
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
        stage.act();
        stage.draw();
    }

    private ImageButton createInventorySlot(){
        Texture upTexture = new Texture("assets/textures/DIRT.png");
        Texture downTexture = new Texture("assets/textures/MUD.png");
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(upTexture);
        style.down = new TextureRegionDrawable(downTexture);
        ImageButton imageButton = new ImageButton(style);
        return imageButton;
    }


    private void handleClick(int row, int col) {
        System.out.println("button " + row + " " + col);
    }
}
