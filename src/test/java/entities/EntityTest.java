package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    @Test
    void testGetId() {
        Entity entity = new Entity(42, null);
        assertEquals(42, entity.getId());
    }

    @Test
    void testGetName() {
        Entity entity = new Entity(0, "player");
        assertEquals("player", entity.getName());
    }

    @Test
    void testSetName() {
        Entity entity = new Entity(0, "player");
        entity.setName("winner");
        assertEquals("winner", entity.getName());
    }

    @Test
    void testSetNameCanSetNameToNull() {
        Entity entity = new Entity(0, "player");
        entity.setName(null);
        assertNull(entity.getName());
    }

    @Test
    void testToStringReturnsNameIfNotNull() {
        Entity entity = new Entity(0, "player");
        assertEquals("0 player", entity.toString());
    }

    @Test
    void testToStringReturnsEntityIfNameIsNull() {
        Entity entity = new Entity(0, null);
        assertEquals("0 entity", entity.toString());
    }
}