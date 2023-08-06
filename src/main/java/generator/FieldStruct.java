package generator;

public class FieldStruct {
    public GroundType groundType;
    public ObjectType objectType;
    public int data0;
    public int data1;
    public int data2;

    public FieldStruct(GroundType groundType, ObjectType objectType, int data0, int data1, int data2) {
        this.groundType = groundType;
        this.objectType = objectType;
        this.data0 = data0;
        this.data1 = data1;
        this.data2 = data2;
    }

    public FieldStruct(GroundType groundType, ObjectType objectType) {
        this(groundType, objectType, 0, 0, 0);
    }
}
