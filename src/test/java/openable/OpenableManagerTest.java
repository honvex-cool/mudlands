package openable;

import org.junit.jupiter.api.Test;
import systems.InputSystem;

import static org.junit.jupiter.api.Assertions.*;

class OpenableManagerTest {
    @Test
    void checkIfBooleansAreSetCorrectly(){
        OpenableManager openableManager = new OpenableManager(new InputSystem());
        openableManager.setBooleans(false, true, false);
        assertFalse(openableManager.isInventoryOpen());
        assertTrue(openableManager.isCraftingOpen());
        assertFalse(openableManager.isStatusOpen());
    }

    @Test
    void updateMethodTest() {
        InputSystem inputSystem = new InputSystem();
        OpenableManager openableManager = new OpenableManager(inputSystem);
        inputSystem.setInventoryClicked(true);
        inputSystem.setCraftingClicked(false);
        inputSystem.setStatusClicked(false);
        openableManager.update();
        assertTrue(openableManager.isInventoryOpen());
        assertFalse(openableManager.isCraftingOpen());
        assertFalse(openableManager.isStatusOpen());
        inputSystem.setInventoryClicked(false);
        inputSystem.setCraftingClicked(true);
        inputSystem.setStatusClicked(false);
        openableManager.update();
        assertFalse(openableManager.isInventoryOpen());
        assertTrue(openableManager.isCraftingOpen());
        assertFalse(openableManager.isStatusOpen());
        inputSystem.setInventoryClicked(false);
        inputSystem.setCraftingClicked(false);
        inputSystem.setStatusClicked(true);
        openableManager.update();
        assertFalse(openableManager.isInventoryOpen());
        assertFalse(openableManager.isCraftingOpen());
        assertTrue(openableManager.isStatusOpen());
        inputSystem.setInventoryClicked(false);
        inputSystem.setCraftingClicked(false);
        inputSystem.setStatusClicked(false);
        openableManager.update();
        assertFalse(openableManager.isInventoryOpen());
        assertFalse(openableManager.isCraftingOpen());
        assertFalse(openableManager.isStatusOpen());
    }
}