package openable.items.tools;

import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;
import utils.Pair;

import java.util.ArrayList;

public class AxeItem extends Item {
    String name = "Axe";
    private final int requiredStone = 3;
    private final int requiredStick = 2;
    private final StoneItem stone = new StoneItem();
    private final StickItem stick = new StickItem();


    public AxeItem() {
        stackable = false;
        craftable = true;
        edible = false;
        equipable = true;
        usable = false;
        attackStrength = 40;
        createRecipe();
    }

    private void createRecipe(){
        recipe = new ArrayList<>();
        recipe.add(new Pair<>(stone, requiredStone));
        recipe.add(new Pair<>(stick, requiredStick));
    }


    @Override
    public boolean craft(Inventory inventory) {
        if(!super.craft(inventory)){
            return false;
        }
        if(inventory.checkInventory(stone, requiredStone) && inventory.checkInventory(stick, requiredStick)){
            inventory.addItem(new AxeItem(), 1);
            inventory.removeItem(stone, requiredStone);
            inventory.removeItem(stick, requiredStick);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
