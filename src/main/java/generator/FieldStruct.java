package generator;

import java.io.Serializable;
import java.util.Objects;

public class FieldStruct implements Serializable {
    public GroundType groundType;//0-99 100-999
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
        groundType = GroundType.values()[0];
    }

    public FieldStruct(GroundType groundType, ObjectType objectType) {
        this(groundType, objectType, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public FieldStruct(FieldStruct struct) {
        this(struct.groundType, struct.objectType, struct.data0, struct.data1, struct.data2);
    }

    public void applyDiffs(FieldStruct diff_field) {
        this.groundType = diff_field.groundType;
        this.objectType = diff_field.objectType;
        this.data0 = diff_field.data0;
        this.data1 = diff_field.data1;
        this.data2 = diff_field.data2;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        FieldStruct that = (FieldStruct) o;
        return data0 == that.data0 && data1 == that.data1 && data2 == that.data2 && groundType == that.groundType && objectType == that.objectType;
    }
}
