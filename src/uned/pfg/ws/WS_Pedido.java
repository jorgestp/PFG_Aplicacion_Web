package uned.pfg.ws;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.PedidoDAO;

public class WS_Pedido {

	private PedidoDAO pedidoDAO;
	
	@Resource(name = "jdbc/prueba")
	private DataSource datasource;
	BasicDataSource basicDataSource;
	
	public WS_Pedido() {
		
		basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("");
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/prueba_pfg");

		basicDataSource.setValidationQuery("select 1");
		
		
		
		pedidoDAO = new PedidoDAO(basicDataSource);
		
	}
	
	public String envioPedido() {
		
		String a = pedidoDAO.crearXML(pedidoDAO.obtenPedidos());
		return a;
	}
}
