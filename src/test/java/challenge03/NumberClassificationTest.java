package challenge03;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberClassificationTest {

    @Test
    void testIsPositive() {
        assertTrue(NumberClassification.isPositive(10));
        assertFalse(NumberClassification.isPositive(-5));
        assertFalse(NumberClassification.isPositive(0));
    }

    @Test
    void testIsNegative() {
        assertTrue(NumberClassification.isNegative(-10));
        assertFalse(NumberClassification.isNegative(10));
        assertFalse(NumberClassification.isNegative(0));
    }

    @Test
    void testIsEven() {
        assertTrue(NumberClassification.isEven(8));
        assertTrue(NumberClassification.isEven(0));
        assertFalse(NumberClassification.isEven(3));
    }

    @Test
    void testIsPrime() {
        assertFalse(NumberClassification.isPrime(0));
        assertFalse(NumberClassification.isPrime(1));
        assertTrue(NumberClassification.isPrime(2));
        assertTrue(NumberClassification.isPrime(3));
        assertFalse(NumberClassification.isPrime(4));
        assertTrue(NumberClassification.isPrime(13));
    }

    @Test
    void testClassify() {
        assertEquals("zero", NumberClassification.classify(0));
        assertEquals("negative", NumberClassification.classify(-4));
        assertEquals("positive, even", NumberClassification.classify(4));
        assertEquals("positive, prime, odd", NumberClassification.classify(3));
        assertEquals("positive, odd", NumberClassification.classify(9));
    }

}
