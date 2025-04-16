
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

        
        // Redirect standard output to a buffer so we can inspect it later

    
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        
        // Call the main method (which should prompt for input and then print output)

        XMLParser.main(null);
    
        String output = out.toString();

        // Check that the output contains the expected field in JSON format

        assertTrue(output.contains("\"name\"")); // simple check for JSON structure

        // Reset standard output back to the console

        System.setOut(System.out);
    }
    
}
