package openable.inventory;

import openable.inventory.InventoryField;
import openable.items.Stick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryFieldTest {
    @Test
    void testConstructor(){
        InventoryField field = new InventoryField(new Stick(), 20);
        assertEquals("Stick", field.getItem().toString());
        assertEquals(20, field.getNumber());
        assertTrue(field.getItem().isStackable());
        assertFalse(field.getItem().isEdible());
    }

    @Test
    void testClearField(){
        InventoryField field = new InventoryField(new Stick(), 20);
        field.clearField();
        assertEquals("None", field.getItem().toString());
        assertEquals(0, field.getNumber());
        assertFalse(field.getItem().isStackable());
        assertFalse(field.getItem().isEdible());
    }

    @Test
    void testSetField(){
        InventoryField field = new InventoryField(new Stick(), 20);
        InventoryField field2 = new InventoryField();
        assertEquals("None", field2.getItem().toString());
        assertEquals(0, field2.getNumber());
        assertFalse(field2.getItem().isStackable());
        assertFalse(field2.getItem().isEdible());
        field2.setField(field);
        assertEquals("Stick", field2.getItem().toString());
        assertEquals(20, field2.getNumber());
        assertTrue(field2.getItem().isStackable());
        assertFalse(field2.getItem().isEdible());
    }
}