package openable.crafting;

import openable.items.tools.AxeItem;
import openable.items.tools.MudPickaxeItem;
import openable.items.tools.PotionItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {

    @Test
    void addItemToTheNextPlace() {
        Page page = new Page("TestPage", 42);
        page.addItem(new PotionItem());
        page.addItem(new MudPickaxeItem());
        page.addItem(new AxeItem());
        assertEquals("Potion", page.getItem(0).toString());
        assertEquals("MudPickaxe", page.getItem(1).toString());
        assertEquals("Axe", page.getItem(2).toString());
    }

    @Test
    void addItemToSpecificPlace() {
        Page page = new Page("TestPage", 42);
        page.addItem(new PotionItem(), 10);
        page.addItem(new MudPickaxeItem(), 21);
        page.addItem(new AxeItem(), 6);
        assertEquals("Potion", page.getItem(10).toString());
        assertEquals("MudPickaxe", page.getItem(21).toString());
        assertEquals("Axe", page.getItem(6).toString());
    }

    @Test
    void checkPageSize() {
        Page page = new Page("TestPage", 20);
        assertEquals(20, page.getSize());
        assertEquals("TestPage", page.toString());
    }
}