package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_Actualizar_Embalado {
	

	private PedidoDAO pedidoDAO;
	private BasicDataSource basicDataSource;
	
	public WS_Actualizar_Embalado() {


		basicDataSource = PoolConexiones.getInstance().getConnection();
		pedidoDAO = new PedidoDAO(basicDataSource);
	}

	public String actualizaEmbalado(String id_pedido, String id_articulo) {
		
		boolean flag = pedidoDAO.actualizarEmbalado(Integer.parseInt(id_pedido), Integer.parseInt(id_articulo));
		
		if(flag) 	return "exito";
		else return "error";
		
		
		
	}

}
