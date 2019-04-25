package uned.pfg.ws;


import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_Pedido {

	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	public WS_Pedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	public String envioPedido() {
		
		String a = pedidoDAO.crearXML(pedidoDAO.obtenPedidos());
		return a;
	}
	

	
	
	
}
