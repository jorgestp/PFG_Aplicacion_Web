package uned.pfg.controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.ArticuloPedido;
import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.PedidoDAO;
import uned.pfg.ws.PoolConexiones;

/**
 * Servlet implementation class ControllerEstadoPedido
 */
@WebServlet("/ControllerEstadoPedido")
public class ControllerEstadoPedido extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PedidoDAO pedidoDAO;
	private BasicDataSource basicDataSource;
	
	//@Resource(name="jdbc/prueba")
	//private DataSource pool;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			
			pedidoDAO = new PedidoDAO(basicDataSource);
		
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}

	/**
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("HOLA POST");

	}

}
