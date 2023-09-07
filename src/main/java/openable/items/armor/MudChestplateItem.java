package openable.items.armor;

import openable.inventory.Inventory;
import openable.items.materials.MudEssenceItem;
import utils.Pair;

public class MudChestplateItem extends LeatherChestplateItem {
    public MudChestplateItem(){
        super();
        name = "MudChestplate";
        recipe.add(new Pair<>(new MudEssenceItem(), 8));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudChestplateItem());
    }
}
