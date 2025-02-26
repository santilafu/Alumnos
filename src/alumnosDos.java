import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class alumnosDos {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/actividad";
        String user = "root";
        String password = "2048C@mpeon1";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM alumno");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element rootElement = document.createElement("alumnos");
            document.appendChild(rootElement);

            while (resultSet.next()) {
                Element alumno = document.createElement("alumno");

                Element nombre = document.createElement("nombre");
                nombre.appendChild(document.createTextNode(resultSet.getString("nombre")));
                alumno.appendChild(nombre);

                Element apellidos = document.createElement("apellidos");
                apellidos.appendChild(document.createTextNode(resultSet.getString("apellidos")));
                alumno.appendChild(apellidos);

                Element telefono = document.createElement("telefono");
                telefono.appendChild(document.createTextNode(resultSet.getString("telefono")));
                alumno.appendChild(telefono);

                Element direccion = document.createElement("direccion");
                direccion.appendChild(document.createTextNode(resultSet.getString("direccion")));
                alumno.appendChild(direccion);

                Element cp = document.createElement("cp");
                cp.appendChild(document.createTextNode(resultSet.getString("cp")));
                alumno.appendChild(cp);

                Element email = document.createElement("email");
                email.appendChild(document.createTextNode(resultSet.getString("email")));
                alumno.appendChild(email);

                rootElement.appendChild(alumno);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("alumno2.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo alumno2.xml generado exitosamente.");

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
