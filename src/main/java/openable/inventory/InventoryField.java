package openable.inventory;

import openable.items.Item;
import openable.items.None;

public class InventoryField {
    private Item item;
    private int number;

    InventoryField() {
        this(new None(), 0);
    }

    InventoryField(Item item, int number) {
        this.item = item;
        this.number = number;
    }

    public Item getItem() {
        return item;
    }

    public int getNumber() {
        return number;
    }



    public void clearField() {
        item = new None();
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
}
