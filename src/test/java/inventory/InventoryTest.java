package inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

class InventoryTest {
    @Test
    void testConstructor() {
        Inventory inventory = new Inventory();
        assertEquals(INVENTORY_WIDTH, inventory.getFields().getWidth());
        assertEquals(INVENTORY_HEIGHT, inventory.getFields().getHeight());
    }

    @Test
    void testAddItem() {
        Inventory inventory = new Inventory();
        InventoryField field = new InventoryField(InventoryFieldType.APPLE, 20, true, false);
        inventory.addItem(field, 0, 0);
        assertEquals(InventoryFieldType.APPLE, inventory.get(0, 0).getItemType());
        assertEquals(20, inventory.get(0, 0).getNumber());
        assertTrue(inventory.get(0, 0).isEdible());
        assertFalse(inventory.get(0, 0).isEquippable());
    }

    @Test
    void testRemoveItem() {
        Inventory inventory = new Inventory();
        InventoryField field = new InventoryField(InventoryFieldType.APPLE, 20, true, false);
        inventory.addItem(field, 0, 0);
        assertEquals(InventoryFieldType.APPLE, inventory.get(0, 0).getItemType());
        assertEquals(20, inventory.get(0, 0).getNumber());
        assertTrue(inventory.get(0, 0).isEdible());
        assertFalse(inventory.get(0, 0).isEquippable());
        inventory.removeItem(0, 0);
        assertEquals(InventoryFieldType.NONE, inventory.get(0, 0).getItemType());
        assertEquals(0, inventory.get(0, 0).getNumber());
        assertFalse(inventory.get(0, 0).isEdible());
        assertFalse(inventory.get(0, 0).isEquippable());

    }
}