package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_Pedidos_con_Prod_Acabados {

	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	public WS_Pedidos_con_Prod_Acabados() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	public String envioPedido() {
		
		String a = pedidoDAO.crearXML(pedidoDAO.obtenPedidosConProdAcabados());
		return a;
	}
}
