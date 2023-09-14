package openable.inventory;

import openable.items.armor.LeatherHelmetItem;
import openable.items.armor.LeatherLeggingsItem;
import openable.items.armor.MudHelmetItem;
import openable.items.armor.MudLeggingsItem;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import openable.items.materials.ZombieBloodItem;
import openable.items.structures.StoneFenceItem;
import openable.items.tools.AxeItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFieldTest {
    @Test
    void testConstructor() {
        InventoryField field = new InventoryField(new AxeItem(), 1, ItemType.RIGHT_HAND);
        assertEquals("Axe", field.getItem().toString());
        assertEquals(1, field.getNumber());
        assertFalse(field.getItem().isStackable());
        assertFalse(field.getItem().isEdible());
        assertTrue(field.getItem().isCraftable());
    }

    @Test
    void testClearField() {
        InventoryField field = new InventoryField(new StickItem(), 20, ItemType.NONE);
        field.clearField();
        assertEquals("None", field.getItem().toString());
        assertEquals(0, field.getNumber());
        assertFalse(field.getItem().isStackable());
        assertFalse(field.getItem().isEdible());
    }

    @Test
    void testSetField() {
        InventoryField field = new InventoryField(new StoneItem(), 20, ItemType.NONE);
        InventoryField field2 = new InventoryField();
        assertEquals("None", field2.getItem().toString());
        assertEquals(0, field2.getNumber());
        assertFalse(field2.getItem().isStackable());
        assertFalse(field2.getItem().isEdible());
        field2.setField(field);
        assertEquals("Stone", field2.getItem().toString());
        assertEquals(20, field2.getNumber());
        assertTrue(field2.getItem().isStackable());
        assertFalse(field2.getItem().isEdible());
    }

    @Test
    void acceptInventoryFieldTest() {
        InventoryField field = new InventoryField(ItemType.HEAD);
        LeatherHelmetItem leatherHelmetItem = new LeatherHelmetItem();
        MudHelmetItem mudHelmetItem = new MudHelmetItem();
        assertTrue(field.accept(leatherHelmetItem));
        assertTrue(field.accept(mudHelmetItem));
    }

    @Test
    void rejectInventoryFieldTest() {
        InventoryField field = new InventoryField(ItemType.HEAD);
        LeatherLeggingsItem leatherLeggingsItem = new LeatherLeggingsItem();
        MudLeggingsItem mudLeggingsItem = new MudLeggingsItem();
        assertFalse(field.accept(leatherLeggingsItem));
        assertFalse(field.accept(mudLeggingsItem));
    }

    @Test
    void acceptInventoryFieldWithNoneType() {
        InventoryField field = new InventoryField(ItemType.NONE);
        StoneFenceItem stoneFenceItem = new StoneFenceItem();
        ZombieBloodItem zombieBloodItem = new ZombieBloodItem();
        assertTrue(field.accept(stoneFenceItem));
        assertTrue(field.accept(zombieBloodItem));
    }
}