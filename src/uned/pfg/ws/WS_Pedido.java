package uned.pfg.ws;


import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;


/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo obtener la lista de pedidos
 * que tiene el sistema
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Pedido {

	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_Pedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	/**
	 * Funcion que tiene por objetivo obtener todos los pedidos que hay en el sistema en ese momento.
	 * @return String que representa al xml con todos los pedidos y sus valores que han sido
	 * seleccionados
	 */
	public String envioPedido() {
		
		String a = pedidoDAO.crearXML(pedidoDAO.obtenPedidos());
		return a;
	}
	

	
	
	
}
