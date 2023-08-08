package inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void testConstructorDefault(){
        Grid grid = new Grid();
        assertEquals(7, grid.getWidth());
        assertEquals(6, grid.getHeight());
    }
    @Test
    void testConstructor(){
        Grid grid = new Grid(50, 20);
        assertEquals(50, grid.getWidth());
        assertEquals(20, grid.getHeight());
        assertEquals(20,grid.inventoryFields.size());
        assertEquals(50, grid.inventoryFields.get(0).size());
    }

    @Test
    void getField() {
        Grid grid = new Grid();
        assertEquals(0, grid.inventoryFields.get(0).get(0).getNumber());
    }
}