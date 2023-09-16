package openable.items.armor;

import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.LeatherItem;
import utils.Pair;

public class LeatherLeggingsItem extends Item {
    private final LeatherItem leather = new LeatherItem();

    public LeatherLeggingsItem() {
        name = "Leggings";
        type = ItemType.LEGS;
        edible = false;
        stackable = false;
        craftable = true;
        createRecipe();
    }

    private void createRecipe() {
        int requiredLeather = 7;
        recipe.add(new Pair<>(leather, requiredLeather));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new LeatherLeggingsItem());
    }
}
