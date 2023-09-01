package components;

import java.io.Serializable;

public interface Vital extends Serializable {
    int getCurrentPoints();
    int getMaxPoints();

    static boolean isDrained(Vital vital) {
        return vital.getCurrentPoints() <= 0;
    }

    static float asFraction(Vital vital) {
        return vital.getCurrentPoints() / (float)(vital.getMaxPoints());
    }
}
