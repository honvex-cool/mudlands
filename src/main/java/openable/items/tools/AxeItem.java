package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import utils.Pair;

import java.util.ArrayList;

public class AxeItem extends Item{
    private final int requiredStone = 3;
    private final int requiredStick = 2;
    private final StoneItem stone = new StoneItem();
    private final StickItem stick = new StickItem();

    public AxeItem() {
        name = "Axe";
        stackable = false;
        craftable = true;
        edible = false;
        damage = new Damage(20, 1, 1, 10);
        type = ItemType.RIGHT_HAND;
        createRecipe();
    }

    private void createRecipe(){
        recipe.add(new Pair<>(stone, requiredStone));
        recipe.add(new Pair<>(stick, requiredStick));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new AxeItem());
    }
}
