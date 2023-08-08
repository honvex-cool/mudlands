package utils;

import java.util.Map;

public interface Savable {
    Map<Integer, Integer> saveData();
    void construct(Map<Integer, Integer> struct, boolean generated);
}
