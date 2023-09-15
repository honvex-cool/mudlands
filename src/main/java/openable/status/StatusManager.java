package openable.status;

import entities.Player;
import openable.inventory.Inventory;
import openable.items.Item;

public class StatusManager {

    private Item head, chest, legs, boots, rightHand, leftHand;


    private final Inventory inventory;

    public StatusManager(Inventory inventory) {
        this.inventory = inventory;
        head = inventory.getHead();
        chest = inventory.getChest();
        legs = inventory.getLegs();
        boots = inventory.getLegs();
        rightHand = inventory.getRightHand();
        leftHand = inventory.getLeftHand();
    }

    public void updateStatus(){
        head = inventory.getHead();
        chest = inventory.getChest();
        legs = inventory.getLegs();
        boots = inventory.getBoots();
        rightHand = inventory.getRightHand();
        leftHand = inventory.getLeftHand();
    }

    public Item getHead() {
        return head;
    }

    public Item getChest() {
        return chest;
    }

    public Item getLegs() {
        return legs;
    }

    public Item getBoots() {
        return boots;
    }

    public Item getRightHand() {
        return rightHand;
    }

    public Item getLeftHand() {
        return leftHand;
    }
}
