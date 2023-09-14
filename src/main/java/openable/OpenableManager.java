package openable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import systems.InputSystem;

public class OpenableManager {

    private boolean inventoryOpen;
    private boolean craftingOpen;
    private boolean statusOpen;
    private final InputSystem inputSystem;

    public OpenableManager(InputSystem inputSystem) {
        this.inputSystem = inputSystem;
        inventoryOpen = false;
        craftingOpen = false;
        statusOpen = false;
    }

    protected void setBooleans(boolean inv, boolean craft, boolean status) {
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
        } else if(Gdx.input == null || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
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
