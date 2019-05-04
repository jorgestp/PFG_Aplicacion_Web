package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.DistribuidorDAO;

public class WS_Distri_de_Pedido {
	
	
	private DistribuidorDAO distribuidorDAO;
	

	BasicDataSource basicDataSource;
	
	public WS_Distri_de_Pedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
	}
	
	public String envioDistribuidor(String id) {
		
		return distribuidorDAO.crearXML_un_Distribuidor(distribuidorDAO.obtenDistribuidor_porID(Integer.parseInt(id)));
		
	}

}
