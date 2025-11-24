package challenge00;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldTest {

    @Test
    void testGetMessage() {
        String result = HelloWorld.getMesssage();
        assertEquals("Hello, World!", result);
    }
}
