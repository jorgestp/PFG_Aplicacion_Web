package uned.pfg.ws;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uned.pfg.bean.Articulo;

import uned.pfg.dao.ArticuloDAO;


/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo introducir en la base de datos un nuevo
 * articulo.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_NuevoArticulo {

	private ArticuloDAO articuloDAO;
	private BasicDataSource basicDataSource;
	private static final String ARCHIVO = "nuevoArtRecibido.xml";
	private Articulo art;

	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de articulo.
	 */
	public WS_NuevoArticulo() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		articuloDAO = new ArticuloDAO(basicDataSource);
	}

	/**
	 * Funcion que introduce en la base de datos, usando el objeto DAO de Articulo, un nuevo
	 * articulo en el sistema.
	 * 
	 * Parsea el parametro que se le pasa, que se trata de un xml en formato String con los
	 * valores del nuevo pedido, a un bean de Articulo, y una vez parseado, ya se introduce en el
	 * sistema
	 * @param articulo String que representa a un xml de un articulo con todos sus valores
	 * @return exito en el caso de introducirse correctamente en la base de datos, o error, en el caso
	 * de lanzarse alguna excepcion de conexion con la base de datos o con el propio servidor.
	 */
	public String nuevoArticulo(String articulo) {

		parsearXML_a_Articulo(articulo);
		if(articuloDAO.introducirArticulo(art)) {
			
			return "exito";
		}
		
		
		return "error";

	}

	/*
	 * Funcion privada que pasa el xml que se le pasa en formato String por parametro a este metodo,
	 * en un objeto de tipo Articulo usando el DOM de Java
	 */
	private Articulo parsearXML_a_Articulo(String articulo) {

		try (FileWriter file = new FileWriter(ARCHIVO)) {
			PrintWriter pw = new PrintWriter(file);
			pw.print(articulo);
		} catch (Exception e) {

			e.printStackTrace();
		}
		try {

			String[] aux;
			aux = new String[3];

			DocumentBuilderFactory fabricaCreadorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder creadorDocumento = fabricaCreadorDocumento.newDocumentBuilder();
			Document documento = creadorDocumento.parse(ARCHIVO);

			Element raiz = documento.getDocumentElement();

			// Obtener la lista de nodos que tienen etiqueta "reserva"
			NodeList listaEmpleados = raiz.getElementsByTagName("articulo");
			// Recorrer la lista de reserva

			for (int i = 0; i < listaEmpleados.getLength(); i++) {

				Node empleado = listaEmpleados.item(i);

				NodeList datosEmpleado = empleado.getChildNodes();

				for (int j = 0; j < datosEmpleado.getLength(); j++) {
					// Obtener de la lista de datos un dato tras otro
					Node dato = datosEmpleado.item(j);

					// Comprobar que el dato se trata de un nodo de tipo Element
					if (dato.getNodeType() == Node.ELEMENT_NODE) {

						Node datoContenido = dato.getFirstChild();
						// Mostrar el valor contenido en el nodo que debe ser de tipo Text
						if (datoContenido != null && datoContenido.getNodeType() == Node.TEXT_NODE)

							aux[j] = datoContenido.getNodeValue();
					}
				}

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date_entrada = formatter.parse(aux[1]);
				art = new Articulo(aux[0], date_entrada, Double.parseDouble(aux[2]));

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return art;
	}
}
