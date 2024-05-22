import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.XMLReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class XmlReader {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the fields you want to display (comma-separated): ");
            String[] fields = scanner.nextLine().split(",");
            Set<String> validFields = new HashSet<>(Arrays.asList("name", "postalZip", "region", "country", "address", "list"));

            for (String field : fields) {
                if (!validFields.contains(field.trim())) {
                    System.out.println("Invalid field: " + field);
                    return;
                }
            }

           
