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

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.Distribuidor;
import uned.pfg.bean.Pedido;
import uned.pfg.dao.DistribuidorDAO;
import uned.pfg.dao.PedidoDAO;
import uned.pfg.ws.PoolConexiones;
import uned.pfg.ws.WS_Pedido;

/**
 * Servlet implementation class ControllerLogin
 */
@WebServlet("/ControllerLogin")
public class ControllerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	//@Resource(name="jdbc/prueba")
	//private DataSource pool;
	private BasicDataSource basicDataSource;
	private DistribuidorDAO distribuidorDAO;
	private PedidoDAO pedidoDAO;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			
			distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
			pedidoDAO = new PedidoDAO(basicDataSource);
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
		
		/*String hola = PedidoDAO.diHola();
		List<Pedido> list = pedidoDAO.obtenPedidos();
		
		String xml = pedidoDAO.crearXML(list);
		System.out.println(hola);
		System.out.println(xml);*/
		
		WS_Pedido ped = new WS_Pedido();
		
		String x = ped.envioPedido();
		
		System.out.println(x);
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
