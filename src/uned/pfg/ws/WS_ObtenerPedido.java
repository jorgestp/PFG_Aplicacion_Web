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

public class WS_ObtenerPedido {

	
	private PedidoDAO pedidoDAO;
	private static final String ARCHIVO = "nuevoPedidoRecibido.xml";
	private static final String ARCHIVO2 = "XML_articulosPedido.xml";
	

	private BasicDataSource basicDataSource;
	
	public WS_ObtenerPedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
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
				System.out.println("ENTRADA => " + aux[1] + " => " + date_entrada.toString());
				System.out.println("ENVIO => " + aux[2] + " => " + date_envio.toString());

				p = new Pedido(Integer.parseInt(aux[0]), date_entrada, date_envio, aux[3], null);
				

			}

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return p;
	}
}
