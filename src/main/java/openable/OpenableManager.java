package openable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import openable.crafting.CraftingManager;
import openable.inventory.InventoryManager;
import openable.status.StatusManager;
import systems.InputSystem;

public class OpenableManager {

    private StatusManager statusManager;

    private CraftingManager craftingManager;

    private InventoryManager inventoryManager;

    private boolean inventoryOpen;
    private boolean craftingOpen;
    private boolean statusOpen;
    private InputSystem inputSystem;

    public OpenableManager(InputSystem inputSystem, InventoryManager inventoryManager, CraftingManager craftingManager, StatusManager statusManager) {
        this.inputSystem = inputSystem;
        this.statusManager = statusManager;
        this.craftingManager = craftingManager;
        this.inventoryManager = inventoryManager;
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
        } else if(inputSystem.isCraftingClicked()) {
            setBooleans(false, true, false);
        } else if(inputSystem.isStatusClicked()) {
            setBooleans(false, false, true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            setBooleans(false, false, false);
        }
    }

    public boolean isInventoryOpen() {
        return inventoryOpen;
    }

    public boolean isCraftingOpen() {
        return craftingOpen;
    }

    public boolean isStatusOpen() {
        return statusOpen;
    }

}
