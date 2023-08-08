package utils;

import entities.EntityTag;

import java.io.Serializable;
import java.util.Map;

public class SaveStruct implements Serializable {
    public EntityTag entityTag;
    public int type;
    public Map<Integer,Integer> data;
    public float x,y;
    public SaveStruct(EntityTag entityTag, int type, float x, float y,Map<Integer,Integer> data){
        this.entityTag = entityTag;
        this.type = type;
        this.x = x;
        this.y = y;
        this.data = data;
    }
}
