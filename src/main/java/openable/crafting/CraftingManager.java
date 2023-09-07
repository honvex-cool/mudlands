package openable.crafting;

import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.structures.StoneFenceItem;
import openable.items.structures.WoodenFenceItem;
import openable.items.structures.WoodenGateItem;
import openable.items.tools.*;

import java.util.ArrayList;

public class CraftingManager {

    private int page;

    private final int maxPage;

    private final ArrayList<Page> pages;

    private final Inventory inventory;

    public CraftingManager(Inventory inventory) {
        this.inventory = inventory;
        pages = new ArrayList<>();
        createPages();
        maxPage = pages.size() - 1;
    }

    private void createPages() {
        Page page = new Page("Tools");
        page.addItem(new PickaxeItem());
        page.addItem(new MudPickaxeItem());
        page.addItem(new SwordItem());
        page.addItem(new MudSwordItem());
        page.addItem(new AxeItem());
        page.addItem(new MudAxeItem());
        page.addItem(new PotionItem());
        page.addItem(new WoodenFenceItem());
        page.addItem(new StoneFenceItem());
        page.addItem(new WoodenGateItem());
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
