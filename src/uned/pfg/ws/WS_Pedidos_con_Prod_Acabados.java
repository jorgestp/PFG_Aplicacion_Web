package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo obtener la lista de pedidos
 * que tiene el sistema ya acabados, es decir, que ningun articulo de esos pedidos este aun 
 * por fabricar
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Pedidos_con_Prod_Acabados {

	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_Pedidos_con_Prod_Acabados() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	/**
	 * Funcnio que, usando el objeto DAO de pedio que ha sido inicializado en el constructor, obtiene todos
	 * los pedidos cuyo articulos ya esten realizados.
	 * @return String que representa al xml con los pedidos que se buscan
	 */
	public String envioPedido() {
		
		String a = pedidoDAO.crearXML(pedidoDAO.obtenPedidosConProdAcabados());
		return a;
	}
}
