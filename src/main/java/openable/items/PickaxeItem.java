package openable.items;

import openable.inventory.Inventory;

import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

public class PickaxeItem extends Item {
    String name = "Pickaxe";
    private final int requiredStone = 3;
    private final int requiredStick = 2;
    private int stones;
    private int sticks;

    public PickaxeItem() {
        stackable = false;
        craftable = true;
        edible = false;
        equipable = true;
        usable = false;
    }

    @Override
    public void craft(Inventory inventory) {
        stones = 0;
        sticks = 0;
        for(int i = 0; i < INVENTORY_HEIGHT; i++) {
            for(int j = 0; j < INVENTORY_WIDTH; j++) {
                if(inventory.get(i, j).getItem().toString().equals("Stone")) {
                    stones += inventory.get(i, j).getNumber();
                }
                if(inventory.get(i, j).getItem().toString().equals("Stick")) {
                    sticks += inventory.get(i, j).getNumber();
                }
            }
        }
        if(stones >= requiredStone && sticks >= requiredStick) {
            inventory.addItem(new PickaxeItem(), 1);
            inventory.removeItem(inventory.searchItem(new StoneItem()).getFirst(), inventory.searchItem(new StoneItem()).getSecond(), requiredStone);
            inventory.removeItem(inventory.searchItem(new StickItem()).getFirst(), inventory.searchItem(new StickItem()).getSecond(), requiredStick);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
