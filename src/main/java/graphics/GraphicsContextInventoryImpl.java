package graphics;

import com.badlogic.gdx.Gdx;
import entities.Player;
import utils.AssetManager;

public class GraphicsContextInventoryImpl implements GraphicsContextInventory {

    private final CraftingRendering craftingRendering;
    private final InventoryRendering inventoryRendering;
    private final StatusRendering statusRendering;

    boolean invOpen = false, craftOpen = false, statusOpen = false;

    public GraphicsContextInventoryImpl(AssetManager assetManager) {
        craftingRendering = new CraftingRendering(assetManager);
        statusRendering = new StatusRendering(assetManager);
        inventoryRendering = new InventoryRendering(assetManager);
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
    public void setPlayer(Player player) {
        statusRendering.setPlayer(player.getInventory());
        craftingRendering.setPlayer(player.getInventory());
        inventoryRendering.setPlayer(player);
    }

    @Override
    public void dispose() {
        craftingRendering.dispose();
        inventoryRendering.dispose();
        statusRendering.dispose();
    }
}
