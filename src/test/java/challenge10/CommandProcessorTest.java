package challenge10;

import challenge10.protocol.CommandProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorTest {

    private final CommandProcessor processor = new CommandProcessor();

    @Test
    void helloShouldReturnWelcome() {
        assertEquals("WELCOME x!", processor.processCommand("HELLO x"));
    }

    @Test
    void helloWithoutNameShouldReturnError() {
        assertTrue(processor.processCommand("HELLO").startsWith("ERROR"));
    }

    @Test
    void echoShouldReturnSameMessage() {
        assertEquals("hi there", processor.processCommand("ECHO hi there"));
    }

    @Test
    void timeShouldReturnTimePrefix() {
        assertTrue(processor.processCommand("TIME").startsWith("TIME "));
    }

    @Test
    void quitShouldReturnBye() {
        assertEquals("Goodbye!", processor.processCommand("QUIT"));
    }

    @Test
    void unknownCommandShouldReturnError() {
        assertEquals("ERROR Unknown command -> ABC", processor.processCommand("ABC"));
    }

    @Test
    void blankCommandShouldReturnError() {
        assertTrue(processor.processCommand("   ").startsWith("ERROR"));
    }


}
