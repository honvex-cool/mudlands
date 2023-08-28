package graphics.drawable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformTest {
    @Test
    void testConstructor() {
        Transform transform = new Transform(7, 42, 20, 15);
        assertEquals(7, transform.x());
        assertEquals(42, transform.y());
        assertEquals(20, transform.width());
        assertEquals(15, transform.height());
    }

    @Test
    void testDefaultConstructor() {
        Transform transform = new Transform();
        assertEquals(0, transform.x());
        assertEquals(0, transform.y());
        assertEquals(1, transform.width());
        assertEquals(1, transform.height());
    }

    @Test
    void testEquals() {
        Transform first = new Transform(7, 42, 20, 15);
        Transform second = new Transform(7, 42, 20, 15);
        Transform third = new Transform(15, 20, 7, 42);
        assertEquals(first, second);
        assertEquals(second, first);
        assertNotEquals(first, third);
        assertNotEquals(third, first);
        assertNotEquals(second, third);
        assertNotEquals(third, second);
    }

    @Test
    void testWithX() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(5, 1, 1, 1);
        assertEquals(expected, original.withX(5));
        assertEquals(1, original.x());
    }

    @Test
    void testShiftedX() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(6, 1, 1, 1);
        assertEquals(expected, original.shiftedX(5));
        assertEquals(1, original.x());
    }

    @Test
    void testWithY() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(1, 5, 1, 1);
        assertEquals(expected, original.withY(5));
        assertEquals(1, original.y());
    }

    @Test
    void testShiftedY() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(1, 6, 1, 1);
        assertEquals(expected, original.shiftedY(5));
        assertEquals(1, original.y());
    }

    @Test
    void testWithPosition() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(5, 5, 1, 1);
        assertEquals(expected, original.withPosition(5, 5));
        assertEquals(1, original.x());
        assertEquals(1, original.y());
    }

    @Test
    void testShifted() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(6, 6, 1, 1);
        assertEquals(expected, original.shifted(5, 5));
        assertEquals(1, original.x());
        assertEquals(1, original.y());
    }

    @Test
    void testWithWidth() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(1, 1, 3, 1);
        assertEquals(expected, original.withWidth(3));
        assertEquals(1, original.width());
    }

    @Test
    void testWithHeight() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(1, 1, 1, 3);
        assertEquals(expected, original.withHeight(3));
        assertEquals(1, original.height());
    }

    @Test
    void testWithDimensions() {
        Transform original = new Transform(1, 1, 1, 1);
        Transform expected = new Transform(1, 1, 3, 3);
        assertEquals(expected, original.withDimensions(3, 3));
        assertEquals(1, original.width());
        assertEquals(1, original.height());
    }
}