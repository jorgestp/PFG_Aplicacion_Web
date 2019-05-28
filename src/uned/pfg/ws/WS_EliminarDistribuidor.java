package uned.pfg.ws;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.Distribuidor;
import uned.pfg.dao.DistribuidorDAO;
import uned.pfg.dao.PedidoDAO;


/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo eliminar un distribuidor
 * del sistema, siempre que este no tenga ningun pedido activo.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_EliminarDistribuidor {

	
	private PedidoDAO pedidoDAO;
	private DistribuidorDAO distribuidorDAO;

	BasicDataSource basicDataSource;
	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de pedido y distribuidor.
	 */
	public WS_EliminarDistribuidor() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
	}
	
	/**
	 * Funcion que busca el distribuidor a eliminar con el nombre pasado por parametro.
	 * Posteriormente comprueba que no tenga pedidos activos en el sistema; si es asi, no se
	 * podra eliminar aun el distribuidor.
	 * @param nombre String que representa al nombre del distribuidor a eliminar
	 * @return exito, si el distribuidor no tiene pedidos activos y se puede eliminar. Error en caso contrario.
	 */
	public String eliminaDistribuidor(String nombre) {
		
		String result = "error";
		
		Distribuidor d = distribuidorDAO.obtenDistri(nombre);
		
		if(pedidoDAO.tienePedidoDistribuidor(d.getId()) ==0) {
			
			//se puede borrar
			try {
				distribuidorDAO.eliminaDistribuidor(d.getId());
				
			} catch (NumberFormatException e) {
				
				return result;
			} catch (SQLException e) {
				
				return result;
			}
			
			result = "exito";
			return result;
			
		}
		
		
		return result;
	}
	
	
}
