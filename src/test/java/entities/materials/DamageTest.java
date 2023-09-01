package entities.materials;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DamageTest {
    @Test
    void testConstructor() {
        // negative damage values are not allowed
        assertThrows(IllegalArgumentException.class, () -> new Damage(-1, 2, 3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Damage(1, -2, 3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Damage(1, 2, -3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Damage(1, 2, 3, -4));

        // and neither are zeros
        assertThrows(IllegalArgumentException.class, () -> new Damage(0, 2, 3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Damage(1, 0, 3, 4));
        assertThrows(IllegalArgumentException.class, () -> new Damage(1, 2, 0, 4));
        assertThrows(IllegalArgumentException.class, () -> new Damage(1, 2, 3, 0));
    }

    @Test
    void testAgainst() {
        Damage fist = new Damage(1, 1, 1, 1);
        Damage axe = new Damage(10, 1, 1, 1);
        Damage universal = new Damage(5, 5, 5, 5);
        Damage powerfulAxe = new Damage(20, 1, 1, 1);

        Mix pureWood = new Mix(100, 0, 0, 0);
        assertEquals(1, fist.against(pureWood));
        assertEquals(10, axe.against(pureWood));
        assertEquals(5, universal.against(pureWood));
        assertEquals(20, powerfulAxe.against(pureWood));

        Mix treeMonster = new Mix(50, 0, 0, 50);
        assertEquals(1, fist.against(treeMonster));
        assertEquals(6, axe.against(treeMonster));
        assertEquals(5, universal.against(treeMonster));
        assertEquals(11, powerfulAxe.against(treeMonster));
    }
}