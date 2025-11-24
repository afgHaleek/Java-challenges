package challenge05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class StringManipulationTest {

    @Test
    void testCountCharsFormatted() {
        String result = StringManipulation.countCharsFormatted("abc d");
        assertEquals("a b c d\nTotal: 4", result);
    }

    @Test
    void testReverseString() {
        assertEquals("cba", StringManipulation.reverseString("abc"));
        assertEquals("madam", StringManipulation.reverseString("madam"));
    }

    @Test
    void testManualToUpper() {
        assertEquals("HELLO", StringManipulation.manualToUpper("hello"));
    }

    @Test
    void testManualToLower() {
        assertEquals("hello", StringManipulation.manualToLower("HELLO"));
    }

    @Test
    void testCountWords() {
        assertEquals(4, StringManipulation.countWords("I love Java Programming"));
        assertEquals(1, StringManipulation.countWords("Hello"));
        assertEquals(0, StringManipulation.countWords("  "));
    }

    @Test
    void testManualIsPalindrome() {
        assertTrue(StringManipulation.manualIsPalindrome("madam"));
        assertFalse(StringManipulation.manualIsPalindrome("hello"));
    }
}
