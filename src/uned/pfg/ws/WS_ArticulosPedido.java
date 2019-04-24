package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_ArticulosPedido {

	private PedidoDAO pedidoDAO;
	

	BasicDataSource basicDataSource;
	
	public WS_ArticulosPedido() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	
	public String envioArticulos(String id_pedido) {
		
		String respuesta = pedidoDAO.crearXML_Articulos(pedidoDAO.obtenArticulosPedido(Integer.parseInt(id_pedido)));
		
		return respuesta;
	}
	
}
