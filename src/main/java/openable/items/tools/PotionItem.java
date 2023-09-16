package openable.items.tools;

import entities.Player;
import openable.inventory.Inventory;
import openable.items.Item;
import openable.items.materials.GhostEssenceItem;
import openable.items.materials.MudEssenceItem;
import openable.items.materials.ZombieBloodItem;
import utils.Pair;

public class PotionItem extends Item {

    private final GhostEssenceItem ghost = new GhostEssenceItem();
    private final ZombieBloodItem blood = new ZombieBloodItem();
    private final MudEssenceItem mud = new MudEssenceItem();

    public PotionItem() {
        name = "Potion";
        edible = true;
        stackable = true;
        craftable = true;
        createRecipe();
    }

    private void createRecipe(){
        int requiredGhost = 1;
        recipe.add(new Pair<>(ghost, requiredGhost));
        int requiredZombie = 1;
        recipe.add(new Pair<>(blood, requiredZombie));
        int requiredMud = 1;
        recipe.add(new Pair<>(mud, requiredMud));
    }

    @Override
    public void use(Player player) {
        int hp = 100;
        player.heal(hp);
    }

    @Override
    public boolean craft(Inventory inventory) {
        return tryCrafting(inventory, new PotionItem());
    }
}
