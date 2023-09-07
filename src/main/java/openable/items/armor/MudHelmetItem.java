package openable.items.armor;

import openable.inventory.Inventory;
import openable.items.materials.MudEssenceItem;
import utils.Pair;

public class MudHelmetItem extends LeatherHelmetItem{
    public MudHelmetItem(){
        super();
        name = "MudHelmet";
        recipe.add(new Pair<>(new MudEssenceItem(), 5));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudHelmetItem());
    }
}
