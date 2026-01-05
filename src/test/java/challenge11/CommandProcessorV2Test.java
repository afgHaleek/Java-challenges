package challenge11;

import challenge11.protocol.CommandProcessorV2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CommandProcessorV2Test {

    private final CommandProcessorV2 processor = new CommandProcessorV2();

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

    //TESTS FOR TESTING 'NAME' AND 'MSG' COMMANDS
    @Test
    void nameShouldSetName() {
        assertEquals("OK NAME daoud", processor.processCommand("NAME daoud"));
    }

    @Test
    void msgShouldReturnBroadcast() {
        assertEquals("BROADCAST hello", processor.processCommand("MSG hello"));
    }

    @Test
    void nameWithoutValueShouldError() {
        assertTrue(processor.processCommand("NAME").startsWith("ERROR"));
    }

    @Test
    void msgWithoutValueShouldError() {
        assertTrue(processor.processCommand("MSG").startsWith("ERROR"));
    }

    @Test
    void whoShouldReturnWHO() {
        assertEquals("WHO", processor.processCommand("WHO"));
    }
    //END OF TESTS FOR 'NAME' AND 'MSG' COMMANDS



}
