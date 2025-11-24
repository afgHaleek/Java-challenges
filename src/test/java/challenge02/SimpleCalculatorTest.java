package challenge02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleCalculatorTest {
    SimpleCalculator calculator = new SimpleCalculator();

    @Test
    void testAddition() {
        assertEquals(8, calculator.calculate(5, 3, "+"));
    }

    @Test
    void testSubtraction() {
        assertEquals(2, calculator.calculate(4, 2, "-"));
    }

    @Test
    void testMultiplication() {
        assertEquals(15, calculator.calculate(5, 3, "*"));
    }

    @Test
    void testDivision() {
        assertEquals(2, calculator.calculate(6, 3, "/"));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.calculate(5, 0, "/"));
    }

    @Test
    void testInvalidOperation() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(2, 4, "&"));
    }

}
