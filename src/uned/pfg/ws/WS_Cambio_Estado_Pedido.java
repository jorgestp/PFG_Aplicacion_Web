package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo cambiar el estado
 * de un pedido.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Cambio_Estado_Pedido {
	
	
	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido.
	 */
	public WS_Cambio_Estado_Pedido() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
	
	}
	
	/**
	 * Funcion que busca un pedido en la base de datos mediante el objeto DAO de pedido
	 * inicializado en el constructor de esta clase, mediante su id de pedido y cambiandole
	 * a ese pedido el valor de su estado con el segundo argumento de los parametros.
	 * @param id_pedido String que representa un identificador de un pedido
	 * @param estado String que representa al estado al que se le quiere cambiar el pedido
	 * @return exito, si el cambio no ha significado ninguna excepcion de conexion o caida del servidor, o false,
	 * si no se ha realizado el cambio de estado por alguna de las circunstancias de excepcion mencionadas.
	 */
	public String cambiarEstado(String id_pedido, String estado) {
		
		try {
			
		pedidoDAO.cambiarEstadoPedido(Integer.parseInt(id_pedido), estado);
		return "exito";
		
		}catch (Exception e) {

			e.printStackTrace();
			return "error";
		}
		
	}

}
