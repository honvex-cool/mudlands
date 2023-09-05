package graphics;

import com.badlogic.gdx.Gdx;
import openable.crafting.CraftingManager;
import openable.inventory.InventoryManager;
import openable.status.StatusManager;
import utils.AssetManager;

public class GraphicsContextInventoryImpl implements GraphicsContextInventory {

    private final CraftingRendering craftingRendering;
    private final InventoryRendering inventoryRendering;
    private final StatusRendering statusRendering;

    boolean invOpen = false, craftOpen = false, statusOpen = false;

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
        if(inv && !invOpen){
            inventoryRendering.updateInventory();
            Gdx.input.setInputProcessor(inventoryRendering.getStage());
            invOpen = true;
            craftOpen = false;
            statusOpen = false;
        }
        if(craft && !craftOpen){
            Gdx.input.setInputProcessor(craftingRendering.getStage());
            craftOpen = true;
            invOpen = false;
            statusOpen = false;
        }
        if(status && !statusOpen){
            statusRendering.updateInventory();
            Gdx.input.setInputProcessor(statusRendering.getStage());
            statusOpen = true;
            invOpen = false;
            craftOpen = false;
        }
        if(!inv && !craft && !status){
            statusOpen = false;
            invOpen = false;
            craftOpen = false;
        }
        if(inv) {
            inventoryRendering.update();
        }
        else if(craft) {
            craftingRendering.update();
        }
        else if(status) {
            statusRendering.update();
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
