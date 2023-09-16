package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import utils.Pair;

import java.util.ArrayList;

public class SwordItem extends Item {
    private final StoneItem stone = new StoneItem();
    private final StickItem stick = new StickItem();

    public SwordItem() {
        name = "Sword";
        stackable = false;
        craftable = true;
        edible = false;
        damage = new Damage(1, 1, 1, 20);
        type = ItemType.RIGHT_HAND;
        createRecipe();
    }

    private void createRecipe() {
        int requiredStone = 2;
        recipe.add(new Pair<>(stone, requiredStone));
        int requiredStick = 1;
        recipe.add(new Pair<>(stick, requiredStick));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new SwordItem());
    }
}
