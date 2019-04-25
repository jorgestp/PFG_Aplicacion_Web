package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.ArticuloDAO;


public class WS_Articulo {
	
	private ArticuloDAO articuloDAO;
	

	private BasicDataSource basicDataSource;

	
	public WS_Articulo() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		articuloDAO = new ArticuloDAO(basicDataSource);
	}
	
	
	public String envioArticulos() {
		
		return articuloDAO.crearXML_Articulos(articuloDAO.getArticulos());
	}
}
