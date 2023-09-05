package openable.inventory;

import openable.items.Item;
import openable.items.NoneItem;

import java.io.Serializable;

public class InventoryField implements Serializable {
    private Item item;
    private int number;

    private final ItemType type;

    public InventoryField() {
        this(new NoneItem(), 0, ItemType.NONE);
    }

    InventoryField(ItemType type) {
        this(new NoneItem(), 0, type);
    }

    InventoryField(Item item, int number, ItemType type) {
        this.item = item;
        this.number = number;
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public int getNumber() {
        return number;
    }



    public void clearField() {
        item = new NoneItem();
        number = 0;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setField(InventoryField field) {
        this.number = field.getNumber();
        this.item = field.getItem();
    }

    public boolean accept(Item item){
        if(type == ItemType.NONE){
            return true;
        }
        return type == item.getType();
    }
}

