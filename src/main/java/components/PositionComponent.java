package components;

import utils.Config;
import utils.Pair;

public interface PositionComponent extends Component {
    float getX();

    float getY();

    static int getChunkX(PositionComponent positionComponent) {
        return (int)Math.floor(positionComponent.getX() / Config.CHUNK_SIZE);
    }

    static int getChunkY(PositionComponent positionComponent) {
        return (int)Math.floor(positionComponent.getY() / Config.CHUNK_SIZE);
    }

    static Pair<Integer, Integer> getChunk(PositionComponent positionComponent) {
        return new Pair<>(getChunkX(positionComponent), getChunkY(positionComponent));
    }

    static Pair<Integer, Integer> getFieldAsPair(PositionComponent positionComponent) {
        return new Pair<>((int)Math.floor(positionComponent.getX()), (int)Math.floor(positionComponent.getY()));
    }

    @Override
    default void accept(ComponentVisitor visitor) {
        visitor.visit(this);
    }
}
