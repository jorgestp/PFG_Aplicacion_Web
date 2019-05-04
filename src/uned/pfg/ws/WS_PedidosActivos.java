package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_PedidosActivos {

	private PedidoDAO pedidoDAO;

	private BasicDataSource basicDataSource;

	public WS_PedidosActivos() {
		
		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}

	public String envioPedido(String id_dist) {

		int numero = pedidoDAO.pedidosActivos(Integer.parseInt(id_dist));
		return String.valueOf(numero);
	}
}
