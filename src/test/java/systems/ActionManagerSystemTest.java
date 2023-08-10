package systems;

import org.junit.jupiter.api.Test;
import utils.Debug;
import utils.Pair;

import static org.junit.jupiter.api.Assertions.*;

class ActionManagerSystemTest {
    @Test
    void testAngleToVector(){
        ActionManagerSystem actionManagerSystem = new ActionManagerSystem();
        Pair<Float,Float> ans;

        ans = actionManagerSystem.angleToVector(0,1);
        assertEquals(1,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(0,ans.getSecond(),Debug.TEST_DELTA);

        ans = actionManagerSystem.angleToVector(90,1);
        assertEquals(0,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(1,ans.getSecond(),Debug.TEST_DELTA);

        ans = actionManagerSystem.angleToVector(180,1);
        assertEquals(-1,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(0,ans.getSecond(),Debug.TEST_DELTA);

        ans = actionManagerSystem.angleToVector(270,1);
        assertEquals(0,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(-1,ans.getSecond(),Debug.TEST_DELTA);

        ans = actionManagerSystem.angleToVector(30,2);
        assertEquals(Math.sqrt(3),ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(1,ans.getSecond(),Debug.TEST_DELTA);

        ans = actionManagerSystem.angleToVector(-30,4);
        assertEquals(2*Math.sqrt(3),ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(-2,ans.getSecond(),Debug.TEST_DELTA);
    }

}