package openable.crafting;

import openable.items.Item;

import java.util.ArrayList;

public class Page {

    private final String name;
    private final ArrayList<Item> items;

    public Page(String name) {
        this.name = name;
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return name;
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public int getSize() {
        return items.size();
    }
}
