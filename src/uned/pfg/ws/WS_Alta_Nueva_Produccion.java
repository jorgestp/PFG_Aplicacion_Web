package uned.pfg.ws;

import java.io.FileWriter;
import java.io.PrintWriter;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.dao.AlmacenDAO;

public class WS_Alta_Nueva_Produccion {
	
	private static final String ARCHIVO = "nueva_produccion.xml";
	private BasicDataSource basicDataSource;
	private AlmacenDAO almacenDAO;
	private ArticuloPedido artped;
	
	public WS_Alta_Nueva_Produccion() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		almacenDAO = new AlmacenDAO(basicDataSource);
	}
	
	public String nuevaAltaProduccion(String articuloPedido) {
		
		parsearXML_a_Articulo(articuloPedido);
		
		try {
			almacenDAO.altaProduccionArticulo(artped);
		
			return "exito";
		
		}catch (Exception e) {
			
			return "error";
		}
	}
	
	
	
	private ArticuloPedido parsearXML_a_Articulo(String articuloPedido) {

		try (FileWriter file = new FileWriter(ARCHIVO)) {
			PrintWriter pw = new PrintWriter(file);
			pw.print(articuloPedido);
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		try {

			String[] aux;
			aux = new String[2];

			DocumentBuilderFactory fabricaCreadorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder creadorDocumento = fabricaCreadorDocumento.newDocumentBuilder();
			Document documento = creadorDocumento.parse(ARCHIVO);

			Element raiz = documento.getDocumentElement();

			// Obtener la lista de nodos que tienen etiqueta "reserva"
			NodeList listaEmpleados = raiz.getElementsByTagName("articuloPedido");
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

				artped = new ArticuloPedido(new Articulo(Integer.parseInt(aux[0])),
											Integer.parseInt(aux[1]));
				

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return artped;
	}

}
