
package sdp.xml;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class XMLParserTest {

    @Test
    void testMainDoesNotCrash() {
        // Simulate user input
        String simulatedInput = "name,country\n";
        System.setIn(new java.io.ByteArrayInputStream(simulatedInput.getBytes()));
    
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    
        XMLParser.main(null);
    
        String output = out.toString();
        assertTrue(output.contains("\"name\"")); // simple check for JSON structure
        System.setOut(System.out);
    }
    
}
