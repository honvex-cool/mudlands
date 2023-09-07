package openable.items.structures;

import entities.passives.Passive;
import entities.passives.WoodenFence;
import openable.inventory.Inventory;
import openable.inventory.ItemType;
import openable.items.Item;
import openable.items.materials.StickItem;
import openable.items.tools.AxeItem;
import utils.Pair;

public class WoodenFenceItem extends Item implements Placable {
    public WoodenFenceItem() {
        name = "WoodenFence";
        edible = false;
        craftable = true;
        usable = false;
        stackable = true;
        type = ItemType.HANDS;
        recipe.add(new Pair<>(new StickItem(),4));
    }
    @Override
    public boolean craft(Inventory inventory) {
        if(super.craft(inventory)) {
            return tryCrafting(inventory, new WoodenFenceItem());
        }
        return false;
    }
    @Override
    public Passive afterConstruction() {
        return new WoodenFence();
    }
}
