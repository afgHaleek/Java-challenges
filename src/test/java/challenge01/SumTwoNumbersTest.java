package challenge01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SumTwoNumbersTest {

    @Test
    void testPositiveNumbers() {
        assertEquals(8, SumTwoNumbers.sum(3, 5));
    }

    @Test
    void testNegativeNumbers() {
        assertEquals(-8, SumTwoNumbers.sum(-3, -5));
    }

    @Test
    void testMixedNumbers() {
        assertEquals(2, SumTwoNumbers.sum(5, -3));
    }

    @Test
    void testZero() {
        assertEquals(5, SumTwoNumbers.sum(5, 0));
    }
}
