package uned.pfg.ws;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uned.pfg.bean.Articulo;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo introducir un nuevo
 * pedido y sus articulos en el sistema.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_ObtenerPedido {

	
	private PedidoDAO pedidoDAO;
	private static final String ARCHIVO = "nuevoPedidoRecibido.xml";
	private static final String ARCHIVO2 = "XML_articulosPedido.xml";
	

	private BasicDataSource basicDataSource;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_ObtenerPedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	/**
	 * Fucion que, usando el objeto DAO de pedido inicializado en el constructor, se inserta
	 * el pedido y los articulos del mismo en el sistema.
	 * Durante la inserción, se va comprobando si existe stock libre en el almacen de dichos articulos
	 * del pedido, con el fin de cambiar el valor de "realizado" de los articulos a true.
	 * 
	 * @param pedido String que representa un xml con los valores del pedido que se quiere insertar
	 * @param articulo String que representa un xml con los articulos que pertenecen al pedido
	 * que se quiere insertar
	 * @return exito, en caso de que el pedido al completo haya sido introducido.
	 * Error, en caso de que exista alguna excepcion en cuanto a conexion con la base de datos
	 * o el servidor
	 */
	public String envioPedido(String pedido, String articulo) {
		
		Pedido p = parsearXMLaPedido(pedido);
		List<ArticuloPedido> artPed = parsearXMLaArticulo(articulo);
		p.setArticulos(artPed);
		int id_pedido = pedidoDAO.insertaPedido(p);
		p.setId_pedido(id_pedido);
		 boolean flag = pedidoDAO.insertarArticulos(p);
		 if(flag ) return  "exito";
		 else return "error";
	}
	
	/*
	 * Funcion privada que parsea el xml que se le introduce por parametro en formato String
	 * en una lista de Articulos que tiene el Pedido, con el fin de crear los objetos para 
	 * poder insertarlos en la base de datos.
	 */
	
	private List<ArticuloPedido> parsearXMLaArticulo(String articulo) {
		
        try (FileWriter file = new FileWriter(ARCHIVO2)) {
            PrintWriter p = new PrintWriter(file);
            p.print(articulo);
        }catch(Exception e){
            
            e.printStackTrace();
        }
		
		
		List<ArticuloPedido> artPed = new ArrayList<ArticuloPedido>(); 
		try {

			String[] aux;
			aux = new String[4];

			DocumentBuilderFactory fabricaCreadorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder creadorDocumento = fabricaCreadorDocumento.newDocumentBuilder();
			Document documento = creadorDocumento.parse(ARCHIVO2);

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


				
				ArticuloPedido art_p = new ArticuloPedido(new Articulo(Integer.parseInt(aux[0])),
						Integer.parseInt(aux[1]), Boolean.parseBoolean(aux[2]),
						Boolean.parseBoolean(aux[3]));
				
				artPed.add(art_p);

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return artPed;
	
	}

	/*
	 * Funcion privada que parsea el xml que se le introduce por parametro en formato String
	 * en un objeto de tipo Pedido con el fin de poder insertarlo en el sistema.
	 */
	private Pedido parsearXMLaPedido(String pedido) {
		
		Pedido p =null; 
		
		
        try (FileWriter file = new FileWriter(ARCHIVO)) {
            PrintWriter pw = new PrintWriter(file);
            pw.print(pedido);
        }catch(Exception e){
            
            e.printStackTrace();
        }
		try {

			String[] aux;
			aux = new String[4];

			DocumentBuilderFactory fabricaCreadorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder creadorDocumento = fabricaCreadorDocumento.newDocumentBuilder();
			Document documento = creadorDocumento.parse(ARCHIVO);

			Element raiz = documento.getDocumentElement();

			// Obtener la lista de nodos que tienen etiqueta "reserva"
			NodeList listaEmpleados = raiz.getElementsByTagName("pedido");
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
				Date date_envio = formatter.parse(aux[2]);
				p = new Pedido(Integer.parseInt(aux[0]), date_entrada, date_envio, aux[3], null);
				

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return p;
	}
}
