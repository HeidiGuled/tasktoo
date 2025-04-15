package sdp.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

public class XMLParser {

    static Set<String> validFields = Set.of("name", "postalZip", "region", "country", "address", "list");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the fields you want to display (comma-separated): ");
        String[] requestedFields = scanner.nextLine().toLowerCase().split(",");

        Set<String> fieldsToPrint = new HashSet<>();
        for (String field : requestedFields) {
            String trimmed = field.trim();
            if (validFields.contains(trimmed)) {
                fieldsToPrint.add(trimmed);
            } else {
                System.out.println("Warning: Ignoring invalid field '" + trimmed + "'");
            }
        }

        if (fieldsToPrint.isEmpty()) {
            System.out.println("No valid fields selected. Exiting.");
            return;
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(new File("data.xml"), new DefaultHandler() {

                Map<String, String> currentRecord = new HashMap<>();
                StringBuilder content = new StringBuilder();
                boolean inRecord = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    content.setLength(0); // clear buffer
                    if (qName.equals("record")) {
                        currentRecord.clear();
                        inRecord = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    content.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (inRecord && validFields.contains(qName)) {
                        currentRecord.put(qName, content.toString().trim());
                    }

                    if (qName.equals("record")) {
                        // Output selected fields as JSON
                        System.out.println("{");
                        int count = 0;
                        for (String field : fieldsToPrint) {
                            String val = currentRecord.getOrDefault(field, "");
                            String comma = (++count < fieldsToPrint.size()) ? "," : "";
                            System.out.printf("  \"%s\": \"%s\"%s%n", field, val.replace("\"", "\\\""), comma);
                        }
                        System.out.println("}");
                        System.out.println();
                        inRecord = false;
                    }
                }
            });

        } catch (Exception e) {
            System.err.println("An error occurred while parsing the XML: " + e.getMessage());
        }
    }
}
