package graphics.drawable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransformTest {
    @Nested
    class BasicBehaviorTest {
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
        void testCannotConstructWithNegativeDimensions() {
            assertThrows(IllegalArgumentException.class, () -> new Transform(1, 2, -1, 3));
            assertThrows(IllegalArgumentException.class, () -> new Transform(1, 2, 1, -3));
        }

        @Test
        void testCannotConstructWithZeroDimensions() {
            assertDoesNotThrow(() -> new Transform(1, 2, 0, 3));
            assertDoesNotThrow(() -> new Transform(1, 2, 1, 0));
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
        void testRightAndTop() {
            Transform transform = new Transform(10, 20, 5, 8);
            assertEquals(15, transform.right());
            assertEquals(28, transform.top());
        }
    }

    @Nested
    class SimpleModificationTest {
        private Transform original;

        @BeforeEach
        void setUp() {
            original = new Transform(1, 1, 2, 4);
        }

        @Test
        void testWithX() {
            Transform expected = new Transform(5, 1, 2, 4);
            assertEquals(expected, original.withX(5));
            assertEquals(1, original.x());
        }

        @Test
        void testShiftedX() {
            Transform expected = new Transform(6, 1, 2, 4);
            assertEquals(expected, original.shiftedX(5));
            assertEquals(1, original.x());
        }

        @Test
        void testWithY() {
            Transform expected = new Transform(1, 5, 2, 4);
            assertEquals(expected, original.withY(5));
            assertEquals(1, original.y());
        }

        @Test
        void testShiftedY() {
            Transform expected = new Transform(1, 6, 2, 4);
            assertEquals(expected, original.shiftedY(5));
            assertEquals(1, original.y());
        }

        @Test
        void testWithPosition() {
            Transform expected = new Transform(5, 5, 2, 4);
            assertEquals(expected, original.withPosition(5, 5));
            assertEquals(1, original.x());
            assertEquals(1, original.y());
        }

        @Test
        void testShifted() {
            Transform expected = new Transform(6, 6, 2, 4);
            assertEquals(expected, original.shifted(5, 5));
            assertEquals(1, original.x());
            assertEquals(1, original.y());
        }

        @Test
        void testWithWidth() {
            Transform expected = new Transform(1, 1, 3, 4);
            assertEquals(expected, original.withWidth(3));
            assertEquals(2, original.width());
        }

        @Test
        void testScaledWidth() {
            assertEquals(new Transform(1, 1, 4, 4), original.scaledWidth(2));
            assertEquals(new Transform(1, 1, 1, 4), original.scaledWidth(0.5f));
        }

        @Test
        void testWithHeight() {
            Transform expected = new Transform(1, 1, 2, 3);
            assertEquals(expected, original.withHeight(3));
            assertEquals(4, original.height());
        }

        @Test
        void testScaledHeight() {
            assertEquals(new Transform(1, 1, 2, 8), original.scaledHeight(2));
            assertEquals(new Transform(1, 1, 2, 2), original.scaledHeight(0.5f));
        }

        @Test
        void testWithDimensions() {
            Transform expected = new Transform(1, 1, 3, 3);
            assertEquals(expected, original.withDimensions(3, 3));
            assertEquals(2, original.width());
            assertEquals(4, original.height());
        }

        @Test
        void testScaled() {
            assertEquals(new Transform(1, 1, 4, 2), original.scaled(2, 0.5f));
        }
    }

    @Nested
    class AdvancedModificationTest {
        private Transform basis;
        private Transform block;

        @BeforeEach
        void setUp() {
            basis = new Transform(0, 0, 2, 4);
            block = new Transform(0, 0, 4, 2);
        }

        @Test
        void testLeftAlignedOnTopOf() {
            assertEquals(new Transform(0, 4, 4, 2), block.leftAlignedOnTopOf(basis));
        }

        @Test
        void testRightAlignedOnTopOf() {
            assertEquals(new Transform(-2, 4, 4, 2), block.rightAlignedOnTopOf(basis));
        }

        @Test
        void testCenteredOnTopOf() {
            assertEquals(new Transform(-1, 4, 4, 2), block.centeredOnTopOf(basis));
        }

        @Test
        void testBottomAlignedRightOf() {
            assertEquals(new Transform(2, 0, 4, 2), block.bottomAlignedRightOf(basis));
        }

        @Test
        void testTopAlignedRightOf() {
            assertEquals(new Transform(2, 2, 4, 2), block.topAlignedRightOf(basis));
        }

        @Test
        void testCenteredRightOf() {
            assertEquals(new Transform(2, 1, 4, 2), block.centeredRightOf(basis));
        }
    }

    @Nested
    class BoundingBoxTest {
        @Test
        void testBoundingBoxOfNoTransformsIsNull() {
            assertNull(Transform.boundingBox(List.of()));
        }

        @Test
        void testBoundingBoxOfOneItemReturnsIt() {
            Transform original = new Transform(7, 13, 17, 42);
            Transform box = Transform.boundingBox(List.of(original));
            assertEquals(box, original);
        }

        @Test
        void testBoundingBoxOfTwoOrMoreItems() {
            Transform first = new Transform(0, 0, 2, 2);
            Transform second = new Transform(1, 3, 2, 2);
            Transform third = new Transform(2, 2, 2, 2);

            Transform expectedFirstSecond = new Transform(0, 0, 3, 5);
            Transform actualFirstSecond = Transform.boundingBox(List.of(first, second));
            assertEquals(expectedFirstSecond, actualFirstSecond);

            Transform expectedSecondThird = new Transform(1, 2, 3, 3);
            Transform actualSecondThird = Transform.boundingBox(List.of(second, third));
            assertEquals(expectedSecondThird, actualSecondThird);

            Transform expectedThirdFirst = new Transform(0, 0, 4, 4);
            Transform actualThirdFirst = Transform.boundingBox(List.of(third, first));
            assertEquals(expectedThirdFirst, actualThirdFirst);

            Transform expectedAll = new Transform(0, 0, 4, 5);
            Transform actualAll = Transform.boundingBox(List.of(first, second, third));
            assertEquals(expectedAll, actualAll);
        }

        @Test
        void testBoundingBoxDoesNotDependOnOrder() {
            Transform first = new Transform(0, 0, 2, 2);
            Transform second = new Transform(1, 1, 2, 2);
            assertEquals(Transform.boundingBox(List.of(first, second)), Transform.boundingBox(List.of(second, first)));
        }
    }
}