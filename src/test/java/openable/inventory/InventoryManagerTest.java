package openable.inventory;

import entities.Player;
import openable.items.armor.MudBootsItem;
import openable.items.armor.MudLeggingsItem;
import openable.items.food.AppleItem;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import openable.items.structures.WoodenGateItem;
import openable.items.tools.AxeItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {
    @Test
    void getterTest() {
        Player player = new Player();
        Inventory inventory = player.getInventory();
        InventoryManager inventoryManager = new InventoryManager(player);
        assertEquals("None", inventoryManager.getHead().toString());
        assertEquals("None", inventoryManager.getChest().toString());
        inventory.get(2, 7).setItem(new MudLeggingsItem());
        inventory.get(3, 7).setItem(new MudBootsItem());
        assertEquals("MudLeggings", inventoryManager.getLegs().toString());
        assertEquals("MudBoots", inventoryManager.getBoots().toString());
        inventory.get(4, 7).setItem(new AxeItem());
        inventory.get(5, 7).setItem(new WoodenGateItem());
        assertEquals("Axe", inventoryManager.getRightHand().toString());
        assertNotEquals("None", inventoryManager.getLeftHand().toString());
        inventory.addItem(new AppleItem(), 3);
        assertEquals("Apple", inventoryManager.getItem(0, 0).toString());
        assertEquals("Apple", inventoryManager.get(0, 0).getItem().toString());
    }

    @Test
    void removeTest() {
        Player player = new Player();
        Inventory inventory = player.getInventory();
        InventoryManager inventoryManager = new InventoryManager(player);
        inventory.addItem(new AppleItem(), 3);
        inventory.addItem(new StickItem(), 5);
        inventory.addItem(new StoneItem(), 10);
        assertEquals("Stone", inventoryManager.get(0, 2).getItem().toString());
        assertEquals(10, inventoryManager.get(0, 2).getNumber());
        inventoryManager.removeItem(0, 2);
        assertEquals("None", inventoryManager.get(0, 2).getItem().toString());
        assertEquals(0, inventoryManager.get(0, 2).getNumber());
        assertEquals("Stick", inventoryManager.get(0, 1).getItem().toString());
        assertEquals(5, inventoryManager.get(0, 1).getNumber());
        inventoryManager.removeItem(0, 1 , 4);
        assertEquals("Stick", inventoryManager.get(0, 1).getItem().toString());
        assertEquals(1, inventoryManager.get(0, 1).getNumber());

    }


}