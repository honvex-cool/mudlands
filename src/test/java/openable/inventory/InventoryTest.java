package openable.inventory;

import openable.items.materials.StickItem;
import openable.items.tools.SwordItem;
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
        inventory.addItem(new StickItem(), 20);
        assertEquals("Stick", inventory.get(0, 0).getItem().toString());
        assertEquals(20, inventory.get(0, 0).getNumber());
        assertTrue(inventory.get(0, 0).getItem().isStackable());
        assertFalse(inventory.get(0, 0).getItem().isEdible());
    }

    @Test
    void destroyedItemRemovedFromInventory() {
        Inventory inventory = new Inventory();
        inventory.get(4, 7).setItem(
            new SwordItem(){
                @Override
                public void damageItem() {
                    durability = 0;
                }
            });
        inventory.checkItem();
        assertEquals("Sword", inventory.getRightHand().toString());
        inventory.getRightHand().damageItem();
        inventory.checkItem();
        assertNotEquals("Sword", inventory.getRightHand().toString());
    }

    @Test
    void testRemoveItem() {
        Inventory inventory = new Inventory();
        inventory.addItem(new StickItem(), 20);
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