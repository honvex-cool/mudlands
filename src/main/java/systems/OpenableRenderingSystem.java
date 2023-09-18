package systems;

import graphics.GraphicsContextInventory;
import openable.OpenableManager;

public class OpenableRenderingSystem {

    private GraphicsContextInventory graphicsContext;
    private final OpenableManager openableManager;

    public OpenableRenderingSystem(InputSystem inputSystem) {
        this.openableManager = new OpenableManager(inputSystem);
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
