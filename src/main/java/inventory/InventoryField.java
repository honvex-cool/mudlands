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

    public void clearField(){
        fieldType = InventoryFieldType.NONE;
        number = 0;
        edible = false;
        equippable = false;
    }
    public void setFieldType(InventoryFieldType fieldType) {
        this.fieldType = fieldType;
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

    public void setField(InventoryField field){
        this.number = field.getNumber();
        this.equippable = field.isEquippable();
        this.edible = field.isEdible();
        this.fieldType = field.getObjectType();
    }
}
