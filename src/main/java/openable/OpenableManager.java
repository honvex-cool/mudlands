package openable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entities.Player;
import openable.crafting.CraftingRendering;
import openable.inventory.InventoryRendering;
import openable.status.StatusRendering;
import systems.InputSystem;
import utils.AssetManager;

public class OpenableManager {
    private InventoryRendering inventoryRendering;
    private CraftingRendering craftingRendering;
    private StatusRendering statusRendering;
    private Player player;

    private boolean inventoryOpen;
    private boolean craftingOpen;
    private boolean statusOpen;
    private InputSystem inputSystem;

    public OpenableManager(InputSystem inputSystem, Player player, AssetManager assetManager) {
        this.inputSystem = inputSystem;
        this.player = player;
        craftingRendering = new CraftingRendering(player, assetManager);
        inventoryRendering = new InventoryRendering(player, assetManager);
        statusRendering = new StatusRendering(player, assetManager);
        inventoryOpen = false;
        craftingOpen = false;
        statusOpen = false;
    }

    private void setBooleans(boolean inv, boolean craft, boolean status) {
        inventoryOpen = inv;
        craftingOpen = craft;
        statusOpen = status;
    }

    public void update() {
        if(inputSystem.isInventoryClicked()) {
            setBooleans(true, false, false);
            inventoryRendering.updateInventory();
            Gdx.input.setInputProcessor(inventoryRendering.getStage());
        } else if(inputSystem.isCraftingClicked()) {
            setBooleans(false, true, false);
            Gdx.input.setInputProcessor(craftingRendering.getStage());
        } else if(inputSystem.isStatusClicked()) {
            setBooleans(false, false, true);
            Gdx.input.setInputProcessor(statusRendering.getStage());
        } else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setBooleans(false, false, false);
            Gdx.input.setInputProcessor(null);
        }
        if(inventoryOpen) {
            inventoryRendering.update();
        }
        if(craftingOpen) {
            craftingRendering.update();
        }
        if(statusOpen) {
            statusRendering.update();
        }
    }
}
