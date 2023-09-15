package openable.crafting;

import openable.items.Item;
import openable.items.NoneItem;

import java.util.ArrayList;

public class Page {

    private final String name;
    private final Item[] items;

    private final int size;

    int counter = 0;

    public Page(String name, int size) {
        this.name = name;
        this.size = size;
        items = new Item[size];
        for(int i = 0; i < size; i++){
            items[i] = new NoneItem();
        }
    }

    public void addItem(Item item) {
        items[counter] = item;
        counter++;
    }
    public void addItem(Item item, int place) {
        items[place] = item;
    }

    @Override
    public String toString() {
        return name;
    }

    public Item getItem(int index) {
        return items[index];
    }

    public int getSize() {
        return size;
    }
}
