package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.DistribuidorDAO;



public class WS_Distribuidor {
	
	private DistribuidorDAO distribuidorDAO;
	

	BasicDataSource basicDataSource;
	
	public WS_Distribuidor() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
	}
	
	public String envioDistribuidores() {
		
		return distribuidorDAO.crearXML_Distribuidores(distribuidorDAO.obtenerDistribuidores());
		
	}

}
