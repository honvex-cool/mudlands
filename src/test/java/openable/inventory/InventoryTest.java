package openable.inventory;

import openable.items.armor.MudBootsItem;
import openable.items.materials.MudEssenceItem;
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
            new SwordItem() {
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

    @Test
    void damageItemsTest() {
        Inventory inventory = new Inventory();
        inventory.get(3, 7).setItem(new MudBootsItem());
        assertEquals(100, inventory.getBoots().getDurability());
        inventory.damageItems();
        assertNotEquals(100, inventory.getBoots().getDurability());
    }

    @Test
    void repairItemsTest() {
        Inventory inventory = new Inventory();
        inventory.get(3, 7).setItem(new MudBootsItem());
        inventory.get(4, 7).setItem(new SwordItem());
        assertEquals(100, inventory.getBoots().getDurability());
        assertEquals(100, inventory.getRightHand().getDurability());
        inventory.damageItems();
        inventory.getRightHand().damageItem();
        assertNotEquals(100, inventory.getBoots().getDurability());
        assertNotEquals(100, inventory.getRightHand().getDurability());
        inventory.addItem(new MudEssenceItem(), 5);
        inventory.repair();
        assertEquals(100, inventory.getBoots().getDurability());
        assertEquals(100, inventory.getRightHand().getDurability());
    }

    @Test
    void repairItemsTestWithoutMud() {
        Inventory inventory = new Inventory();
        inventory.get(3, 7).setItem(new MudBootsItem());
        inventory.get(4, 7).setItem(new SwordItem());
        assertEquals(100, inventory.getBoots().getDurability());
        assertEquals(100, inventory.getRightHand().getDurability());
        inventory.damageItems();
        inventory.getRightHand().damageItem();
        assertNotEquals(100, inventory.getBoots().getDurability());
        assertNotEquals(100, inventory.getRightHand().getDurability());
        inventory.repair();
        assertNotEquals(100, inventory.getBoots().getDurability());
        assertNotEquals(100, inventory.getRightHand().getDurability());
    }


}