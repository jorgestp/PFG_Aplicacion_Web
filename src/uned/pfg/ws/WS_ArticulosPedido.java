package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;


/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo obtener todos los
 * articulos que tiene un pedido en concreto.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_ArticulosPedido {

	private PedidoDAO pedidoDAO;
	

	BasicDataSource basicDataSource;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_ArticulosPedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	
	/**
	 * Funcion que busca en la base de datos los articulos que pertenece a un pedido en cuestion
	 * y cuyo id de pedido es pasado por parametro.
	 * @param id_pedido String que representa al id del pedido que estamos buscando sus articulos
	 * @return String que representa un xml de los articulos del pedido en cuestion
	 */
	public String envioArticulos(String id_pedido) {
		
		String respuesta = pedidoDAO.crearXML_Articulos(pedidoDAO.obtenArticulosPedido(Integer.parseInt(id_pedido)));
		
		return respuesta;
	}
	
}
