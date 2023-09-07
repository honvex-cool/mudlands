package openable.items.armor;

import openable.inventory.Inventory;
import openable.items.materials.MudEssenceItem;
import utils.Pair;

public class MudBootsItem extends LeatherBootsItem {
    public MudBootsItem(){
        super();
        name = "MudBoots";
        recipe.add(new Pair<>(new MudEssenceItem(), 4));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudBootsItem());
    }
}
