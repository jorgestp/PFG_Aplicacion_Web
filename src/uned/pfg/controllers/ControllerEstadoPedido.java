package uned.pfg.controllers;

import java.io.IOException;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.dbcp.BasicDataSource;
import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.PedidoDAO;


/**
 * Servlet que sirve de para conocer el estado del o de los pedidos de un distribuidor logueado en el sistema.
 * Este servlet sirve como controlador y parte intermedia entre la vista y la logica del sistema, de modo que
 * se recogen los datos pertienentes de la vista, se procean en el objeto DAO correspondiente al pedio
 * como campo de clase, y los valores o el valor resultante es pasado de nuevo a la vista.
 * 
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
@WebServlet("/ControllerEstadoPedido")
public class ControllerEstadoPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PedidoDAO pedidoDAO;
	private BasicDataSource basicDataSource;
	
	//@Resource(name="jdbc/prueba")
	//private DataSource pool;
	
	
	/**
	 * Funcion que inicializa el campo del servlet correspondiente al objeto DAO de pedido.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			
			pedidoDAO = new PedidoDAO(basicDataSource);
		
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}

	/**
	 * Funcion que recoge la orden de la vista, y en funcion de su valor, muestra los pedidos que hay en 
	 * el sistema para un distribuidor logeado y/o los articulos que tiene un pedido en concreto.
	 * 
	 * Si la orden que se recoge desde la vista a la que es llamadao este servlet es nulo, se muestra
	 * una lista de los pedidos que el distribuidor logeado ha ido creando en el sistema. Para ello, se usa
	 * el objeto DAO correspondiente al pedido que recoge todos los pedidos que hay en la base de datos 
	 * que corresponden al distribuidor en cuestion.
	 * 
	 * Por otro lado, si la orden que se recibe es "ver", se recoge el id del pedido que se quiere 
	 * desglosar y el distribudor loegado, y se muestra el o los articulos que ese pedido tiene asignado.
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String comando = request.getParameter("instruccion");
		HttpSession session = request.getSession();
		Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
		request.setAttribute("dist", dist);
		List<Pedido> pedidos = pedidoDAO.obtenerPedidos(dist.getId());
		request.setAttribute("pedido", pedidos);
		
		if(comando == null) {
		

		
		RequestDispatcher dispatcher =request.getRequestDispatcher("/estadoPedido.jsp");
		
		dispatcher.forward(request, response);
		
		}else if(comando.equals("ver")) {
			
			String id_pedido = request.getParameter("id_pedido");
			List<ArticuloPedido> listaArticulos = pedidoDAO.obtenArticulosPedido(Integer.parseInt(id_pedido));
			request.setAttribute("articulos", listaArticulos);
			request.setAttribute("id_pedido", id_pedido);
			RequestDispatcher dispatcher =request.getRequestDispatcher("/estadoPedidoArticulo.jsp");		
			dispatcher.forward(request, response);
			
			
		}
	}



}
