package components;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PositionComponentTest {
    @Test
    void testAccept() {
        ComponentVisitor visitor = mock(ComponentVisitor.class);
        PositionComponent component = new MutablePositionComponent(0, 0);
        component.accept(visitor);
        verify(visitor).visit(component);
    }
}