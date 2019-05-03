package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_ArticulosSinRealizar {
	
	private BasicDataSource basicDataSource;
	private PedidoDAO pedidoDAO;
	
	public WS_ArticulosSinRealizar() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		pedidoDAO = new PedidoDAO(basicDataSource);
	}
	
	public String enviarArticulosSinRealizar() {
		
		
		
		return pedidoDAO.crearXML_ArticulosSinRealizar(pedidoDAO.articulosSinRealizar());
	}

}
