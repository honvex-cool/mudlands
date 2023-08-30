package openable.inventory;

import openable.items.Item;
import openable.items.NoneItem;
import utils.Pair;

import static utils.Config.INVENTORY_HEIGHT;
import static utils.Config.INVENTORY_WIDTH;

public class Inventory {

    private Item rightHand;
    public Grid getFields() {
        return fields;
    }

    private Grid fields;

    public Inventory() {
        fields = new Grid(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        rightHand = new NoneItem();
        printInventory();
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

    public void equipItem(int i, int j) {
        rightHand = get(i, j).getItem();
        removeItem(i, j);
    }

    public Item getRightHand() {
        return rightHand;
    }
}
