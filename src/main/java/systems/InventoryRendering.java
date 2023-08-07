package systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InventoryRendering {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private boolean inventoryOpen;

    public InventoryRendering() {
        spriteBatch = new SpriteBatch();
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
    }
}
