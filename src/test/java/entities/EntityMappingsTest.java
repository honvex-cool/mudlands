package entities;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EntityMappingsTest {
    @Test
    void testMappingsAreBijective() {
        assertTrue(isBijective(EntityMappings.GROUND_MAP));
        assertTrue(isBijective(EntityMappings.PASSIVE_MAP));
    }

    private boolean isBijective(Map<?, ?> map) {
        return Set.copyOf(map.values()).size() == map.size();
    }
}