package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import utils.Pair;

public class PickaxeItem extends Item {
    private final StoneItem stone = new StoneItem();
    private final StickItem stick = new StickItem();


    public PickaxeItem() {
        name = "Pickaxe";
        stackable = false;
        craftable = true;
        edible = false;
        damage = new Damage(1, 20, 1, 1);
        type = ItemType.RIGHT_HAND;
        createRecipe();
    }

    private void createRecipe(){
        int requiredStone = 3;
        recipe.add(new Pair<>(stone, requiredStone));
        int requiredStick = 2;
        recipe.add(new Pair<>(stick, requiredStick));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new PickaxeItem());
    }
}
