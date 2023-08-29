package components;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RotationComponentTest {
    @Test
    void testAccept() {
        ComponentVisitor visitor = mock(ComponentVisitor.class);
        RotationComponent component = new MutableRotationComponent();
        component.accept(visitor);
        verify(visitor).visit(component);
    }
}