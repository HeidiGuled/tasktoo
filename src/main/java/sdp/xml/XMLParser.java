package sdp.xml;

// Import necessary SAX and parser classes
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

public class XMLParser {

    // Define the set of valid field names that can be selected from the XML
    static Set<String> validFields = Set.of("name", "postalZip", "region", "country", "address", "list");

    public static void main(String[] args) {
        // Create a Scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the fields you want to display (comma-separated): ");
        String[] requestedFields = scanner.nextLine().toLowerCase().split(",");

        // Store valid user-selected fields
        Set<String> fieldsToPrint = new HashSet<>();
        for (String field : requestedFields) {
            String trimmed = field.trim();
            // Add only if the field is valid
            if (validFields.contains(trimmed)) {
                fieldsToPrint.add(trimmed);
            } else {
                System.out.println("Warning: Ignoring invalid field '" + trimmed + "'");
            }
        }

        // If user didn't choose any valid fields, exit the program
        if (fieldsToPrint.isEmpty()) {
            System.out.println("No valid fields selected. Exiting.");
            return;
        }

        try {
            // Set up the SAX parser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Parse the XML file using a custom handler
            saxParser.parse(new File("data.xml"), new DefaultHandler() {

                // Store the current <record>'s values
                Map<String, String> currentRecord = new HashMap<>();
                // Used to accumulate character content inside tags
                StringBuilder content = new StringBuilder();
                boolean inRecord = false; // Track if we’re inside a <record> element

                // Triggered at the start of each XML element
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    content.setLength(0); // Clear buffer for new element
                    if (qName.equals("record")) {
                        currentRecord.clear(); // Start a new record
                        inRecord = true;
                    }
                }

                // Called when character data is found inside an element
                @Override
                public void characters(char[] ch, int start, int length) {
                    content.append(ch, start, length);
                }

                // Triggered at the end of each XML element
                @Override
                public void endElement(String uri, String localName, String qName) {
                    // Save the element content if it's a valid field
                    if (inRecord && validFields.contains(qName)) {
                        currentRecord.put(qName, content.toString().trim());
                    }

                    // At the end of a <record>, output the selected fields in JSON format
                    if (qName.equals("record")) {
                        System.out.println("{");
                        int count = 0;
                        for (String field : fieldsToPrint) {
                            String val = currentRecord.getOrDefault(field, "");
                            // Add comma except after last field
                            String comma = (++count < fieldsToPrint.size()) ? "," : "";
                            // Escape quotes in values
                            System.out.printf("  \"%s\": \"%s\"%s%n", field, val.replace("\"", "\\\""), comma);
                        }
                        System.out.println("}");
                        System.out.println();
                        inRecord = false;
                    }
                }
            });

        } catch (Exception e) {
            // Catch and display parsing-related errors
            System.err.println("An error occurred while parsing the XML: " + e.getMessage());
        }
    }
}
