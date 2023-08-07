package inventory;

public class InventoryField {
    private InventoryFieldType fieldType;
    private int number;

    private boolean edible;

    private boolean equippable;

    InventoryField() {
        this(InventoryFieldType.SWORD, 1, false, false);
    }

    InventoryField(InventoryFieldType fieldType, int number, boolean edible, boolean equippable) {
        this.fieldType = fieldType;
        this.number = number;
        this.edible = edible;
        this.equippable = equippable;
    }

    public InventoryFieldType getObjectType() {
        return fieldType;
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
}
