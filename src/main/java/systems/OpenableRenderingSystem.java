package systems;

import graphics.GraphicsContextInventory;
import openable.OpenableManager;
import openable.crafting.CraftingManager;
import openable.inventory.InventoryManager;
import openable.status.StatusManager;

public class OpenableRenderingSystem {

    private GraphicsContextInventory graphicsContext;
    private OpenableManager openableManager;

    public OpenableRenderingSystem(InputSystem inputSystem, InventoryManager inventoryManager, CraftingManager craftingManager, StatusManager statusManager) {
        this.openableManager = new OpenableManager(inputSystem, inventoryManager, craftingManager, statusManager);
    }

    public void setGraphicsContext(GraphicsContextInventory graphicsContext){
        this.graphicsContext = graphicsContext;
    }


    public void update() {
        openableManager.update();
        graphicsContext.begin();
        graphicsContext.end(openableManager.isInventoryOpen(), openableManager.isCraftingOpen(), openableManager.isStatusOpen());
    }
}
