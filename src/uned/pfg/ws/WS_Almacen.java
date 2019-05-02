package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.AlmacenDAO;



public class WS_Almacen {

	private BasicDataSource basicDataSource;
	private AlmacenDAO almacenDAO;
	
	public WS_Almacen() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		almacenDAO = new AlmacenDAO(basicDataSource);
	}
	
	public String enviarAlmacen() {
		
		
		return almacenDAO.crearXML(almacenDAO.obtenAlmacen());
	}
}
