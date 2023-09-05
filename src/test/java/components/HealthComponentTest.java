package components;

import entities.materials.Composition;
import entities.materials.Mix;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HealthComponentTest {
    @Test
    void testAccept() {
        ComponentVisitor visitor = mock(ComponentVisitor.class);
        HealthComponent component = new Composition(new Mix(1, 1, 1, 1));
        component.accept(visitor);
        verify(visitor).visit(component);
    }
}