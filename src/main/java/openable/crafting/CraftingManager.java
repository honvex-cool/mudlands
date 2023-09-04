package openable.crafting;

import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.tools.AxeItem;
import openable.items.tools.PickaxeItem;
import openable.items.tools.PotionItem;
import openable.items.tools.SwordItem;

import java.util.ArrayList;

public class CraftingManager {

    private int page;

    private int maxPage = 0;

    private ArrayList<Page> pages;

    private Inventory inventory;

    public CraftingManager(Inventory inventory) {
        this.inventory = inventory;
        pages = new ArrayList<>();
        createPages();
        maxPage = pages.size() - 1;
    }

    private void createPages() {
        Page page = new Page("Tools");
        page.addItem(new PickaxeItem());
        page.addItem(new SwordItem());
        page.addItem(new AxeItem());
        page.addItem(new PotionItem());
        pages.add(page);
    }

    public boolean updatePage(int value) {
        page += value;
        if(page < 0) {
            page = 0;
            return false;
        }
        if(page > maxPage) {
            page = maxPage;
            return false;
        }
        return true;
    }

    public boolean craft(Item item){
        return item.craft(inventory);
    }
    public Page getCurrentPage() {
        return pages.get(page);
    }
}
