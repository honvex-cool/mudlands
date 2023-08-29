package openable.inventory;

import openable.inventory.InventoryField;
import openable.inventory.InventoryFieldType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFieldTest {
    @Test
    void testConstructor(){
        InventoryField field = new InventoryField(InventoryFieldType.APPLE, 20, true, false);
        assertEquals(InventoryFieldType.APPLE, field.getItemType());
        assertEquals(20, field.getNumber());
        assertTrue(field.isEdible());
        assertFalse(field.isEquippable());
    }

    @Test
    void testClearField(){
        InventoryField field = new InventoryField(InventoryFieldType.APPLE, 20, true, false);
        field.clearField();
        assertEquals(InventoryFieldType.NONE, field.getItemType());
        assertEquals(0, field.getNumber());
        assertFalse(field.isEdible());
        assertFalse(field.isEquippable());
    }

    @Test
    void testSetField(){
        InventoryField field = new InventoryField(InventoryFieldType.APPLE, 20, true, false);
        InventoryField field2 = new InventoryField();
        assertEquals(InventoryFieldType.NONE, field2.getItemType());
        assertEquals(0, field2.getNumber());
        assertFalse(field2.isEdible());
        assertFalse(field2.isEquippable());
        field2.setField(field);
        assertEquals(InventoryFieldType.APPLE, field2.getItemType());
        assertEquals(20, field2.getNumber());
        assertTrue(field2.isEdible());
        assertFalse(field2.isEquippable());
    }
}