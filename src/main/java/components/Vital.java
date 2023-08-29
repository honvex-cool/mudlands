package components;

public interface Vital {
    int getCurrentPoints();
    int getMaxPoints();

    static boolean isDrained(Vital vital) {
        return vital.getCurrentPoints() <= 0;
    }
}
