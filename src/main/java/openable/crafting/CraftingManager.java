package openable.crafting;

import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.armor.*;
import openable.items.materials.LeatherItem;
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
        Page page0 = new Page("Tools", 42);
        page0.addItem(new PickaxeItem(), 0);
        page0.addItem(new MudPickaxeItem(), 1);
        page0.addItem(new SwordItem(), 7);
        page0.addItem(new MudSwordItem(), 8);
        page0.addItem(new AxeItem(), 14);
        page0.addItem(new MudAxeItem(), 15);
        page0.addItem(new PotionItem(), 21);
        pages.add(page0);
        Page page1 = new Page("Armor", 42);
        page1.addItem(new LeatherHelmetItem(), 0);
        page1.addItem(new MudHelmetItem(), 1);
        page1.addItem(new LeatherChestplateItem(), 7);
        page1.addItem(new MudChestplateItem(), 8);
        page1.addItem(new LeatherLeggingsItem(), 14);
        page1.addItem(new MudLeggingsItem(), 15);
        page1.addItem(new LeatherBootsItem(), 21);
        page1.addItem(new MudBootsItem(), 22);
        pages.add(page1);
        Page page2 = new Page("Structures", 42);
        page2.addItem(new WoodenFenceItem());
        page2.addItem(new StoneFenceItem());
        page2.addItem(new WoodenGateItem());
        pages.add(page2);
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

    public boolean craft(Item item) {
        return item.craft(inventory);
    }
    public Page getCurrentPage() {
        return pages.get(page);
    }
}
