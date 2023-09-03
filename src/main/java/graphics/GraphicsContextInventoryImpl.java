package graphics;

import com.badlogic.gdx.Gdx;
import openable.crafting.CraftingManager;
import openable.crafting.CraftingRendering;
import openable.inventory.Inventory;
import openable.inventory.InventoryManager;
import openable.inventory.InventoryRendering;
import openable.status.StatusManager;
import openable.status.StatusRendering;
import utils.AssetManager;

public class GraphicsContextInventoryImpl implements GraphicsContextInventory {

    private CraftingRendering craftingRendering;
    private InventoryRendering inventoryRendering;
    private StatusRendering statusRendering;

    public GraphicsContextInventoryImpl(InventoryManager inventoryManager, CraftingManager craftingManager, StatusManager statusManager, AssetManager assetManager) {
        craftingRendering = new CraftingRendering(craftingManager, assetManager);
        statusRendering = new StatusRendering(statusManager, assetManager);
        inventoryRendering = new InventoryRendering(inventoryManager, assetManager);
    }


    @Override
    public void begin() {
        GraphicsContextInventory.super.begin();
    }

    @Override
    public void end(boolean inv, boolean craft, boolean status) {
        if(inv) {
            inventoryRendering.update();
            Gdx.input.setInputProcessor(inventoryRendering.getStage());
        }
        else if(craft) {
            craftingRendering.update();
            Gdx.input.setInputProcessor(craftingRendering.getStage());
        }
        else if(status) {
            statusRendering.update();
            Gdx.input.setInputProcessor(statusRendering.getStage());
        }
        else{
            Gdx.input.setInputProcessor(null);
        }
    }

    @Override
    public void dispose() {
        craftingRendering.dispose();
        inventoryRendering.dispose();
        statusRendering.dispose();
    }
}
