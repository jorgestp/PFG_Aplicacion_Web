package uned.pfg.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
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


import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.DistribuidorDAO;
import uned.pfg.dao.PedidoDAO;

/**
 * Servlet implementation class ControllerLogin
 */
@WebServlet("/ControllerLogin")
public class ControllerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	@Resource(name="jdbc/prueba")
	private DataSource pool;
	
	private DistribuidorDAO distribuidorDAO;
	private PedidoDAO pedidoDAO;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			distribuidorDAO = new DistribuidorDAO(pool);
		
			pedidoDAO = new PedidoDAO(pool);
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String usuario = request.getParameter("usuario");
		String contra = request.getParameter("contra");
		
		Distribuidor dist = distribuidorDAO.buscarUsuario(usuario, contra);
		
		String hola = PedidoDAO.diHola();
		List<Pedido> list = pedidoDAO.obtenPedidos();
		
		System.out.println(hola);
		System.out.println(list.size());
		if( dist != null) {
			
			
			HttpSession session= request.getSession();
		
			session.setAttribute("usuario", dist);
			request.setAttribute("dist", dist);
			SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
			request.setAttribute("fecha", formateador.format(dist.getFecha()));
			RequestDispatcher dispatcher =request.getRequestDispatcher("/cuentaUsuario.jsp");
			
			dispatcher.forward(request, response);
		}else {
			
			RequestDispatcher dispatcher =request.getRequestDispatcher("/errorLogin.jsp");
			
			dispatcher.forward(request, response);
			
		}
		
	}

}
