import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class alumnos {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/actividad";
        String user = "root";
        String password = "2048C@mpeon1";
        try {
            File file = new File("alumnos.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            Connection connection = DriverManager.getConnection(url, user, password);

            // Obtener nodos correctos
            NodeList nodeList = document.getElementsByTagName("alumno");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                    String apellidos = element.getElementsByTagName("apellidos").item(0).getTextContent();
                    int telefono = Integer.parseInt(element.getElementsByTagName("telefono").item(0).getTextContent());
                    String direccion = element.getElementsByTagName("direccion").item(0).getTextContent();
                    int cp = Integer.parseInt(element.getElementsByTagName("cp").item(0).getTextContent());
                    String email = element.getElementsByTagName("email").item(0).getTextContent();

                    String sql = "INSERT INTO alumno (nombre, apellidos, telefono, direccion, cp, email) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, nombre);
                    statement.setString(2, apellidos);
                    statement.setInt(3, telefono);
                    statement.setString(4, direccion);
                    statement.setInt(5, cp);
                    statement.setString(6, email);
                    statement.executeUpdate();

                    System.out.println("Alumno insertado: " + nombre + " " + apellidos);
                }
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
