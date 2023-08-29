package graphics.drawable;

import graphics.GraphicsContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BinaryOperator;

public class Stacked implements Drawable {
    private final Drawable[] drawables;
    private Transform transform;

    private Stacked(BinaryOperator<Transform> stackingStrategy, Drawable... drawables) {
        this.drawables = drawables;
        stackDrawables(stackingStrategy);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public void setTransform(@NotNull Transform transform) {
        for(Drawable drawable : drawables)
            drawable.setTransform(retransform(drawable.getTransform(), this.transform, transform));
        this.transform = transform;
    }

    private void stackDrawables(BinaryOperator<Transform> stackingStrategy) {
        Collection<Transform> transforms = new ArrayList<>();
        transforms.add(drawables[0].getTransform());
        for(int i = 1; i < drawables.length; i++) {
            Transform previous = drawables[i - 1].getTransform();
            Transform current = drawables[i].getTransform();
            Transform moved = stackingStrategy.apply(previous, current);
            drawables[i].setTransform(moved);
            transforms.add(moved);
        }
        transform = Transform.boundingBox(transforms);
    }

    public static Stacked grouped(Drawable... drawables) {
        return new Stacked((previous, current) -> current, drawables);
    }

    public static Stacked verticallyLeftAligned(Drawable... drawables) {
        return new Stacked((previous, current) -> current.leftAlignedOnTopOf(previous), drawables);
    }

    public static Stacked verticallyRightAligned(Drawable... drawables) {
        return new Stacked((previous, current) -> current.rightAlignedOnTopOf(previous), drawables);
    }

    public static Stacked verticallyCentered(Drawable... drawables) {
        return new Stacked((previous, current) -> current.centeredOnTopOf(previous), drawables);
    }

    public static Stacked horizontallyBottomAligned(Drawable... drawables) {
        return new Stacked((previous, current) -> current.bottomAlignedRightOf(previous), drawables);
    }

    public static Stacked horizontallyTopAligned(Drawable... drawables) {
        return new Stacked((previous, current) -> current.topAlignedRightOf(previous), drawables);
    }

    public static Stacked horizontallyCentered(Drawable... drawables) {
        return new Stacked((previous, current) -> current.centeredRightOf(previous), drawables);
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        for(Drawable drawable : drawables)
            drawable.draw(graphicsContext);
    }

    private static Transform retransform(Transform transform, Transform parent, Transform destination) {
        float widthScale = destination.width() / parent.width();
        float heightScale = destination.height() / parent.height();
        float x = destination.x() + (transform.x() - parent.x()) * widthScale;
        float y = destination.y() + (transform.y() - parent.y()) * heightScale;
        return transform.withPosition(x, y).scaled(widthScale, heightScale);
    }
}
