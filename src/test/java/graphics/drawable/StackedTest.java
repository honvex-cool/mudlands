package graphics.drawable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class StackedTest {
    private Transform basis;
    private Transform block;
    private Drawable basisDrawable;
    private Drawable blockDrawable;

    @BeforeEach
    void setUp() {
        basis = new Transform(0, 0, 4, 8);
        basisDrawable = new PlaceholderDrawable(basis);
        block = new Transform(2, 6, 8, 4);
        blockDrawable = new PlaceholderDrawable(block);
    }

    void assertBoundingBox(Stacked stacked) {
        assertEquals(
            Transform.boundingBox(List.of(basisDrawable.getTransform(), blockDrawable.getTransform())),
            stacked.getTransform()
        );
    }

    void testStacker(Function<Drawable[], Stacked> stacker, Transform expectedBlockPosition) {
        Stacked stacked = stacker.apply(new Drawable[] { basisDrawable, blockDrawable });
        assertEquals(basis, basisDrawable.getTransform());
        assertEquals(expectedBlockPosition, blockDrawable.getTransform());
        assertBoundingBox(stacked);
    }

    @Test
    void testStacking() {
        testStacker(Stacked::grouped, block);
        testStacker(Stacked::verticallyLeftAligned, block.leftAlignedOnTopOf(basis));
        testStacker(Stacked::verticallyRightAligned, block.rightAlignedOnTopOf(basis));
        testStacker(Stacked::verticallyCentered, block.centeredOnTopOf(basis));
        testStacker(Stacked::horizontallyBottomAligned, block.bottomAlignedRightOf(basis));
        testStacker(Stacked::horizontallyTopAligned, block.topAlignedRightOf(basis));
        testStacker(Stacked::horizontallyCentered, block.centeredRightOf(basis));
        testStacker(Stacked::withCommonCenter, block.withCenterAtCenterOf(basis));
    }

    @Test
    void testSetTransform() {
        Stacked group = Stacked.grouped(basisDrawable, blockDrawable);
        Transform transform = new Transform(1, 1, 20, 5);
        group.setTransform(transform);
        assertEquals(new Transform(1, 1, 8, 4), basisDrawable.getTransform());
        assertEquals(new Transform(5, 4, 16, 2), blockDrawable.getTransform());
        assertEquals(transform, group.getTransform());
    }
}