package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo obtener la lista de pedidos
 * que tiene un articulo en concreto
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Pedido_De_Articulo {

	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_Pedido_De_Articulo() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	/**
	 * Funcion que busca, mediante el objeto DAO de pedido, todos los pedidos que llevan el articulo
	 * con identificador pasado por parametro
	 * 
	 * @param id String que representa el identificador de un Articulo
	 * @return String que representa el XML de los Pedidos que han sido seleccionados.
	 */
	public String envioPedido(String id) {
		
		String a = pedidoDAO.crearXML(pedidoDAO.Pedidos_que_tiene_el_articulo(Integer.parseInt(id)));
		return a;
	}
}
