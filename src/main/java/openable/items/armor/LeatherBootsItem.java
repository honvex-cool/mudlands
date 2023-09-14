package openable.items.armor;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.LeatherItem;
import utils.Pair;

public class LeatherBootsItem extends Item {
    private final LeatherItem leather = new LeatherItem();
    private final int requiredLeather = 4;

    public LeatherBootsItem() {
        name = "Boots";
        type = ItemType.BOOTS;
        edible = false;
        stackable = false;
        craftable = true;
        damage = new Damage(100, 100, 100, 100);
        createRecipe();
    }

    private void createRecipe() {
        recipe.add(new Pair<>(leather, requiredLeather));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new LeatherBootsItem());
    }
}
