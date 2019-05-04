package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_Pedido_De_Articulo {

	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	public WS_Pedido_De_Articulo() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	public String envioPedido(String id) {
		
		String a = pedidoDAO.crearXML(pedidoDAO.Pedidos_que_tiene_el_articulo(Integer.parseInt(id)));
		return a;
	}
}
