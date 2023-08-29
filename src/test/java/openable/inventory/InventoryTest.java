package openable.inventory;

import openable.items.Stick;
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
        InventoryField field = new InventoryField(new Stick(), 20);
        inventory.addItem(field, 0, 0);
        assertEquals("Stick", inventory.get(0, 0).getItem().toString());
        assertEquals(20, inventory.get(0, 0).getNumber());
        assertTrue(inventory.get(0, 0).getItem().isStackable());
        assertFalse(inventory.get(0, 0).getItem().isEdible());
    }

    @Test
    void testRemoveItem() {
        Inventory inventory = new Inventory();
        InventoryField field = new InventoryField(new Stick(), 20);
        inventory.addItem(field, 0, 0);
        assertEquals("Stick", inventory.get(0, 0).getItem().toString());
        assertEquals(20, inventory.get(0, 0).getNumber());
        assertTrue(inventory.get(0, 0).getItem().isStackable());
        assertFalse(inventory.get(0, 0).getItem().isEdible());
        inventory.removeItem(0, 0);
        assertEquals("None", inventory.get(0, 0).getItem().toString());
        assertEquals(0, inventory.get(0, 0).getNumber());
        assertFalse(inventory.get(0, 0).getItem().isStackable());
        assertFalse(inventory.get(0, 0).getItem().isEdible());

    }
}