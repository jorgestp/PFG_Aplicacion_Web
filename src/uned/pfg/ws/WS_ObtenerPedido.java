package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_ObtenerPedido {

	
	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	public WS_ObtenerPedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	public String envioPedido(String a, String b) {
		
		
		return a + b + " FUNCIONA!!";
	}
}
