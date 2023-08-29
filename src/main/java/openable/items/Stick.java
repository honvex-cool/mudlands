package openable.items;

public class Stick extends Item{
    String name = "Stick";
    public Stick(){
        stackable = true;
        craftable = false;
        edible = false;
        equipable = true;
        usable = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
