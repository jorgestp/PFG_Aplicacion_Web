package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

/**
 * Clase que representa a un Web Service que tiene como funcion actualizar el valor
 * de Embalado de un articulo en un pedido concreto
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Actualizar_Embalado {
	

	private PedidoDAO pedidoDAO;
	private BasicDataSource basicDataSource;
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedidos.
	 */
	public WS_Actualizar_Embalado() {


		basicDataSource = PoolConexiones.getInstance().getConnection();
		pedidoDAO = new PedidoDAO(basicDataSource);
	}

	/**
	 * Funcion que se encargar de llamar al objeto DAO correspondiente para actualiar el valor
	 * de embalado de el articulo en el pedido concreto.
	 * 
	 * @param id_pedido Entero que representa el id de un pedido concreto
	 * @param id_articulo Entero que representa el id de un articulo.
	 * @return String con valor "exito", en caso de actualizarse correctamente el valor embalado,
	 * o "error", en caso de ocurrir algun error que impida la acutalizacion como el caso de caida del 
	 * servidor, o de la conexion con la base de datos
	 */
	public String actualizaEmbalado(String id_pedido, String id_articulo) {
		
		boolean flag = pedidoDAO.actualizarEmbalado(Integer.parseInt(id_pedido), Integer.parseInt(id_articulo));
		
		if(flag) 	return "exito";
		else return "error";
		
		
		
	}

}
