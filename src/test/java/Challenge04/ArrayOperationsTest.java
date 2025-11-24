package Challenge04;

import challenge04.ArrayOperations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ArrayOperationsTest {

    int[] sample = {5, 2, 9, 1, 7};

    @Test
    void testSumOfArray() {
        assertEquals(24, ArrayOperations.sumOfArray(sample));
        assertEquals(0, ArrayOperations.sumOfArray(new int[]{}));
        assertEquals(-6, ArrayOperations.sumOfArray(new int[]{-1, -2, -3}));
    }

    @Test
    void testMaxNumInArray() {
        assertEquals(9, ArrayOperations.maxNumInArray(sample));
        assertEquals(-5, ArrayOperations.minNumInArray(new int[]{-5,-3,-1}));
        assertThrows(IllegalArgumentException.class,
                () -> ArrayOperations.maxNumInArray(new int[]{}));
    }

    @Test
    void testMinNumInArray() {
        assertEquals(1, ArrayOperations.minNumInArray(sample));
        assertEquals(-5, ArrayOperations.minNumInArray(new int[]{-1,-4,-5}));
        assertThrows(IllegalArgumentException.class,
                () -> ArrayOperations.minNumInArray(new int[]{}));
    }

    @Test
    void testAverage() {
        assertEquals(24 / 5.0, ArrayOperations.average(sample));
        assertEquals(-2.0, ArrayOperations.average(new int[]{-1,-2,-3}));

        assertThrows(IllegalArgumentException.class,
                () -> ArrayOperations.average(new int[]{}));
    }
}
