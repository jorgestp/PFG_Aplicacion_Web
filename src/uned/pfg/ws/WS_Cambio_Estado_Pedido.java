package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_Cambio_Estado_Pedido {
	
	
	private PedidoDAO pedidoDAO;
	

	private BasicDataSource basicDataSource;
	
	public WS_Cambio_Estado_Pedido() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
	
	}
	
	public String cambiarEstado(String id_pedido, String estado) {
		
		try {
			
		pedidoDAO.cambiarEstadoPedido(Integer.parseInt(id_pedido), estado);
		return "exito";
		
		}catch (Exception e) {

			e.printStackTrace();
			return "error";
		}
		
	}

}
