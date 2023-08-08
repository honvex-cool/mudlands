package inventory;

public class InventoryField {
    private InventoryFieldType item;
    private int number;

    private boolean edible;

    private boolean equippable;

    InventoryField() {
        this(InventoryFieldType.NONE, 0, false, false);
    }

    InventoryField(InventoryFieldType fieldType, int number, boolean edible, boolean equippable) {
        this.item = fieldType;
        this.number = number;
        this.edible = edible;
        this.equippable = equippable;
    }

    public InventoryFieldType getItemType() {
        return item;
    }

    public int getNumber() {
        return number;
    }

    public boolean isEquippable() {
        return equippable;
    }

    public boolean isEdible() {
        return edible;
    }

    public void clearField() {
        item = InventoryFieldType.NONE;
        number = 0;
        edible = false;
        equippable = false;
    }

    public void setItem(InventoryFieldType item) {
        this.item = item;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setEdible(boolean edible) {
        this.edible = edible;
    }

    public void setEquippable(boolean equippable) {
        this.equippable = equippable;
    }

    public void setField(InventoryField field) {
        this.number = field.getNumber();
        this.equippable = field.isEquippable();
        this.edible = field.isEdible();
        this.item = field.getItemType();
    }
}
