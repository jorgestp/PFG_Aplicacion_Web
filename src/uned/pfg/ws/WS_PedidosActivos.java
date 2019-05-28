package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo obtener el numero de pedidos que 
 * aun estan activos para un distribuidor en concreto. Es decir, aquellos pedidos que aun tiene algun
 * articulo por hacer de un distribuidor dado.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_PedidosActivos {

	private PedidoDAO pedidoDAO;

	private BasicDataSource basicDataSource;

	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_PedidosActivos() {
		
		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}

	/**
	 * Funcion que, usando el objeto DAO de peido inicializado en el constructor, selecciona el numero
	 * de pedidos que aun no estan realizados.
	 * @param id_dist String que representa al identificador del distribuidor cuyo numero de pedidos activos estamos 
	 * buscando
	 * @return Valor entero que representa el numero de pedidos que aun estan activos para ese distribuidor
	 */
	public String envioPedido(String id_dist) {

		int numero = pedidoDAO.pedidosActivos(Integer.parseInt(id_dist));
		return String.valueOf(numero);
	}
}
