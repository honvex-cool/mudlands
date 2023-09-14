package openable.crafting;

import openable.inventory.Inventory;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import openable.items.tools.PickaxeItem;
import openable.items.tools.SwordItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CraftingManagerTest {

    @Test
    void createCraftingPages() {
        Inventory inventory = new Inventory();
        CraftingManager craftingManager = new CraftingManager(inventory);
        Page page = craftingManager.getCurrentPage();
        assertEquals("Tools", page.toString());
        assertTrue(craftingManager.updatePage(1));
        page = craftingManager.getCurrentPage();
        assertEquals("Armor", page.toString());
        assertTrue(craftingManager.updatePage(1));
        assertFalse(craftingManager.updatePage(1));
        assertTrue(craftingManager.updatePage(-1));
        assertTrue(craftingManager.updatePage(-1));
        assertFalse(craftingManager.updatePage(-1));
    }

    @Test
    void tryCraftingItem() {
        Inventory inventory = new Inventory();
        CraftingManager craftingManager = new CraftingManager(inventory);
        assertFalse(craftingManager.craft(new PickaxeItem()));
        inventory.addItem(new StoneItem(), 3);
        inventory.addItem(new StickItem(), 2);
        assertTrue(craftingManager.craft(new PickaxeItem()));
    }
}