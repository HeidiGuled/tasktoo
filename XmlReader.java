import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;
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

            File inputFile = new File("path/to/your/data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("record");
            JsonArray jsonArray = new JsonArray();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element record = (Element) nList.item(temp);
                JsonObject jsonObject = new JsonObject();

                for (String field : fields) {
                    field = field.trim();
                    jsonObject.addProperty(field, record.getElementsByTagName(field).item(0).getTextContent());
                }
                jsonArray.add(jsonObject);
            }

            System.out.println(jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
