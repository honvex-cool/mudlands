package openable.items.tools;

import entities.materials.Damage;
import openable.inventory.Inventory;
import openable.items.materials.GhostEssenceItem;
import openable.items.materials.MudEssenceItem;
import openable.items.materials.ZombieBloodItem;
import utils.Pair;

public class MudSwordItem extends SwordItem{

    public MudSwordItem(){
        super();
        name = "MudSword";
        damage = new Damage(1, 1, 1, 30);
        durability = 250;
        max_durability = 250;
        MudEssenceItem mud = new MudEssenceItem();
        int requiredMud = 3;
        recipe.add(new Pair<>(mud, requiredMud));
        GhostEssenceItem ghost = new GhostEssenceItem();
        int requiredGhost = 3;
        recipe.add(new Pair<>(ghost, requiredGhost));
        ZombieBloodItem zombie = new ZombieBloodItem();
        int requiredZombie = 3;
        recipe.add(new Pair<>(zombie, requiredZombie));
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new MudSwordItem());
    }
}
