package openable.items.armor;

import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.LeatherItem;
import utils.Pair;

public class LeatherChestplateItem extends Item {
    private final LeatherItem leather = new LeatherItem();

    public LeatherChestplateItem() {
        name = "Chestplate";
        type = ItemType.CHEST;
        edible = false;
        stackable = false;
        craftable = true;
        createRecipe();
    }

    private void createRecipe() {
        int requiredLeather = 8;
        recipe.add(new Pair<>(leather, requiredLeather));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new LeatherChestplateItem());
    }
}
