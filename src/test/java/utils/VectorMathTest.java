package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorMathTest {
    @Test
    void testRotationFromVector(){

        assertEquals(0,VectorMath.getRotationFromVector(new Pair<>(1f,0f)), Debug.TEST_DELTA);
        assertEquals(45,VectorMath.getRotationFromVector(new Pair<>(1f,1f)), Debug.TEST_DELTA);
        assertEquals(90,VectorMath.getRotationFromVector(new Pair<>(0f,1f)), Debug.TEST_DELTA);
        assertEquals(135,VectorMath.getRotationFromVector(new Pair<>(-1f,1f)), Debug.TEST_DELTA);

        assertEquals(180,VectorMath.getRotationFromVector(new Pair<>(-1f,0f)), Debug.TEST_DELTA);
        assertEquals(225,VectorMath.getRotationFromVector(new Pair<>(-1f,-1f)), Debug.TEST_DELTA);
        assertEquals(270,VectorMath.getRotationFromVector(new Pair<>(0f,-1f)), Debug.TEST_DELTA);
        assertEquals(315,VectorMath.getRotationFromVector(new Pair<>(1f,-1f)), Debug.TEST_DELTA);

        assertEquals(150,VectorMath.getRotationFromVector(new Pair<>((float)-Math.sqrt(3),1f)), Debug.TEST_DELTA);
        assertEquals(210,VectorMath.getRotationFromVector(new Pair<>((float)-Math.sqrt(3),-1f)), Debug.TEST_DELTA);
    }

    @Test
    void testVectorFromRotation(){
        Pair<Float,Float> ans;

        ans = VectorMath.getVectorFromRotation(0,1);
        assertEquals(1,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(0,ans.getSecond(),Debug.TEST_DELTA);

        ans = VectorMath.getVectorFromRotation(90,1);
        assertEquals(0,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(1,ans.getSecond(),Debug.TEST_DELTA);

        ans = VectorMath.getVectorFromRotation(180,1);
        assertEquals(-1,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(0,ans.getSecond(),Debug.TEST_DELTA);

        ans = VectorMath.getVectorFromRotation(270,1);
        assertEquals(0,ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(-1,ans.getSecond(),Debug.TEST_DELTA);

        ans = VectorMath.getVectorFromRotation(30,2);
        assertEquals(Math.sqrt(3),ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(1,ans.getSecond(),Debug.TEST_DELTA);

        ans = VectorMath.getVectorFromRotation(-30,4);
        assertEquals(2*Math.sqrt(3),ans.getFirst(), Debug.TEST_DELTA);
        assertEquals(-2,ans.getSecond(),Debug.TEST_DELTA);
    }
}