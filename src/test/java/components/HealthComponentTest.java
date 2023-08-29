package components;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HealthComponentTest {
    @Test
    void testAccept() {
        ComponentVisitor visitor = mock(ComponentVisitor.class);
        HealthComponent component = new MutableHealthComponent(100);
        component.accept(visitor);
        verify(visitor).visit(component);
    }
}