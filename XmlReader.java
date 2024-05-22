import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;

public class XmlReader {
    public static void main(String[] args) {
        try {
            File inputFile = new File("path/to/your/data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element record = (Element) nList.item(temp);
                System.out.println("Name: " + record.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("PostalZip: " + record.getElementsByTagName("postalZip").item(0).getTextContent());
                System.out.println("Region: " + record.getElementsByTagName("region").item(0).getTextContent());
                System.out.println("Country: " + record.getElementsByTagName("country").item(0).getTextContent());
                System.out.println("Address: " + record.getElementsByTagName("address").item(0).getTextContent());
                System.out.println("List: " + record.getElementsByTagName("list").item(0).getTextContent());
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
