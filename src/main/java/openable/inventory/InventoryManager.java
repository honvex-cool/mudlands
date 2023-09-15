package openable.inventory;

import entities.Player;
import openable.items.Item;

public class InventoryManager {

    Player player;
    Inventory inventory;

    public InventoryManager(Player player) {
        this.player = player;
        this.inventory = player.getInventory();
    }

    public Player getPlayer() {
        return player;
    }

    public Item getHead() {
        return inventory.getHead();
    }

    public Item getChest() {
        return inventory.getChest();
    }

    public Item getLegs() {
        return inventory.getLegs();
    }

    public Item getBoots() {
        return inventory.getBoots();
    }

    public Item getRightHand() {
        return inventory.getRightHand();
    }

    public Item getLeftHand() {
        return inventory.getLeftHand();
    }

    public Item getItem(int row, int col) {
        return inventory.get(row, col).getItem();
    }

    public InventoryField get(int row, int col) {
        return inventory.get(row, col);
    }

    public void removeItem(int row, int col, int number){
        inventory.removeItem(row, col, number);
    }

    public void removeItem(int row, int col){
        inventory.removeItem(row, col);
    }

    public void repair(){
        inventory.repair();
    }
}
