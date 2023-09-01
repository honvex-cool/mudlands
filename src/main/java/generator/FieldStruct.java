package generator;

import java.io.Serializable;
import java.util.Objects;

public class FieldStruct implements Serializable {
    public GroundType groundType;
    public ObjectType objectType;

    public FieldStruct(GroundType groundType, ObjectType objectType) {
        this.groundType = groundType;
        this.objectType = objectType;
    }

    public FieldStruct(FieldStruct struct) {
        this(struct.groundType, struct.objectType);
    }

    public void applyDiffs(FieldStruct diff_field) {
        this.groundType = diff_field.groundType;
        this.objectType = diff_field.objectType;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        FieldStruct that = (FieldStruct) o;
        return groundType == that.groundType && objectType == that.objectType;
    }
}
