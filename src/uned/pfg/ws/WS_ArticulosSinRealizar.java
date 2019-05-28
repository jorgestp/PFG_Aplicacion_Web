package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo dar buscar todos los articulos
 * de los pedidos que aun estan sin realizar en el sistema.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_ArticulosSinRealizar {
	
	private BasicDataSource basicDataSource;
	private PedidoDAO pedidoDAO;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_ArticulosSinRealizar() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		pedidoDAO = new PedidoDAO(basicDataSource);
	}
	
	
	/**
	 * Funcion busca en la base de datos mediante el objeto DAO de pedido, todos los articulos
	 * que tengan el valor de realizado a false, de modo que devuelve el sumatorio en caso de que 
	 * un articulo este en mas de un pedido.
	 * @return String que representa el xml con todos los articulos y su cantidad que estan sin realizar
	 */
	public String enviarArticulosSinRealizar() {
		
		
		
		return pedidoDAO.crearXML_ArticulosSinRealizar(pedidoDAO.articulosSinRealizar());
	}

}
