package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import utils.Pair;

import java.util.ArrayList;

public class PickaxeItem extends Item {
    private final int requiredStone = 3;
    private final int requiredStick = 2;
    private final StoneItem stone = new StoneItem();
    private final StickItem stick = new StickItem();


    public PickaxeItem() {
        name = "Pickaxe";
        stackable = false;
        craftable = true;
        edible = false;
        usable = false;
        damage = new Damage(1, 20, 1, 1);
        type = ItemType.HANDS;
        createRecipe();
    }

    private void createRecipe(){
        recipe.add(new Pair<>(stone, requiredStone));
        recipe.add(new Pair<>(stick, requiredStick));
    }

    @Override
    public boolean craft(Inventory inventory) {
        if(inventory.checkInventory(stone, requiredStone) && inventory.checkInventory(stick, requiredStick)){
            inventory.addItem(new PickaxeItem(), 1);
            inventory.removeItem(stone, requiredStone);
            inventory.removeItem(stick, requiredStick);
            return true;
        }
        return false;
    }
}
