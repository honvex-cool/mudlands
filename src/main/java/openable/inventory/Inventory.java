package openable.inventory;

import openable.items.Item;
import openable.items.NoneItem;
import utils.Pair;

import java.io.Serializable;

import java.util.ArrayList;

import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

public class Inventory implements Serializable {

    private InventoryField rightHand;
    private InventoryField leftHand;

    private InventoryField head;
    private InventoryField chest;
    private InventoryField legs;
    private InventoryField boots;

    public Grid getFields() {
        return fields;
    }

    private Grid fields;

    private ArrayList<InventoryField> equipment;

    public Inventory() {
        fields = new Grid(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        rightHand = new InventoryField(ItemType.HANDS);
        head = new InventoryField(ItemType.HEAD);
        chest = new InventoryField(ItemType.CHEST);
        legs = new InventoryField(ItemType.LEGS);
        boots = new InventoryField(ItemType.BOOTS);
        leftHand = new InventoryField(ItemType.HANDS);
        equipment = new ArrayList<>();
        equipment.add(head);
        equipment.add(chest);
        equipment.add(legs);
        equipment.add(boots);
        equipment.add(rightHand);
        equipment.add(leftHand);
        fields.add(0, head);
        fields.add(1, chest);
        fields.add(2, legs);
        fields.add(3, boots);
        fields.add(4, rightHand);
        fields.add(5, leftHand);
        //printInventory();
    }

    public void printInventory() {
        for(int i = 0; i < fields.getHeight(); i++) {
            for(int j = 0; j < fields.getWidth(); j++) {
                System.out.print(fields.get(i).get(j).getItem() + " " + fields.get(i).get(j).getNumber() + " | ");
            }
            System.out.println();
        }
    }

    public InventoryField get(int i, int j) {
        return fields.get(i).get(j);
    }

    public void addItem(Item item, int number) {
        Pair<Integer, Integer> pair = searchItem(item);
        if(pair == null) {
            pair = searchItem(new NoneItem());
            if(pair == null) {
                return;
            }
        }
        InventoryField field = this.get(pair.getFirst(), pair.getSecond());
        field.setItem(item);
        field.setNumber(field.getNumber() + number);
    }

    public boolean checkInventory(Item item, int number) {
        int currentNumber = 0;
        for(int i = 0; i < INVENTORY_HEIGHT; i++) {
            for(int j = 0; j < INVENTORY_WIDTH; j++) {
                if(get(i, j).getItem().toString().equals(item.toString())) {
                    currentNumber += get(i, j).getNumber();
                }
            }
        }
        return currentNumber >= number;
    }

    public Pair<Integer, Integer> searchItem(Item item) {
        for(int i = 0; i < fields.getHeight(); i++) {
            for(int j = 0; j < fields.getWidth(); j++) {
                if(!item.isStackable() && fields.get(i).get(j).getItem().toString().equals("None")) {
                    return new Pair<>(i, j);
                }
                if(item.isStackable() && fields.get(i).get(j).getItem().toString().equals(item.toString())) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    public void removeItem(int i, int j, int number) {
        InventoryField field = fields.get(i).get(j);
        if(field.getNumber() - number == 0) {
            removeItem(i, j);
            return;
        }
        field.setNumber(field.getNumber() - number);
    }

    public void removeItem(int i, int j) {
        InventoryField field = fields.get(i).get(j);
        field.clearField();
    }

    public void removeItem(Item item, int number) {
        var pair = searchItem(item);
        removeItem(pair.getFirst(), pair.getSecond(), number);
    }

    public void checkItem() {
        Item rightHandItem = rightHand.getItem();
        if(rightHandItem.getDurability() == 0){
            rightHand.clearField();
        }
    }

    public Item getRightHand() {
        return rightHand.getItem();
    }


    public Item getHead() {
        return head.getItem();
    }

    public Item getChest() {
        return chest.getItem();
    }

    public Item getLegs() {
        return legs.getItem();
    }

    public Item getBoots() {
        return boots.getItem();
    }

    public Item getLeftHand() {
        return leftHand.getItem();
    }

}
