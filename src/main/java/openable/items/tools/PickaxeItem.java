package openable.items.tools;

import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.materials.StoneItem;

public class PickaxeItem extends Item {
    String name = "Pickaxe";
    private final int requiredStone = 3;
    private final int requiredStick = 2;
    private final StoneItem stone = new StoneItem();
    private final StickItem stick = new StickItem();


    public PickaxeItem() {
        stackable = false;
        craftable = true;
        edible = false;
        equipable = true;
        usable = false;
        attackStrength = 20;
    }

    @Override
    public void craft(Inventory inventory) {
        if(inventory.checkInventory(stone, requiredStone) && inventory.checkInventory(stick, requiredStick)){
            inventory.addItem(new PickaxeItem(), 1);
            inventory.removeItem(stone, requiredStone);
            inventory.removeItem(stick, requiredStick);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
