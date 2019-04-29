package uned.pfg.ws;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.Distribuidor;
import uned.pfg.dao.DistribuidorDAO;
import uned.pfg.dao.PedidoDAO;

public class WS_EliminarDistribuidor {

	
	private PedidoDAO pedidoDAO;
	private DistribuidorDAO distribuidorDAO;

	BasicDataSource basicDataSource;
	
	public WS_EliminarDistribuidor() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
	}
	
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
