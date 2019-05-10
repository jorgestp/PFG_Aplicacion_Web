package uned.pfg.controllers;

import java.io.IOException;

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
import uned.pfg.dao.DistribuidorDAO;

/**
 * Servlet implementation class ControllerDatosPersonales
 */
@WebServlet("/ControllerDatosPersonales")
public class ControllerDatosPersonales extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BasicDataSource basicDataSource;
	
	/*@Resource(name="jdbc/prueba")
	private DataSource pool;*/
	
	private DistribuidorDAO distribuidorDAO;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
		request.setAttribute("dist", dist);
		RequestDispatcher dispatcher =request.getRequestDispatcher("/cuentaUsuario.jsp");
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Distribuidor dist = (Distribuidor) session.getAttribute("usuario");
		int id = dist.getId();
		String nombre = request.getParameter("nombre");
		String domicilio = request.getParameter("domicilio");
		String email = request.getParameter("email");
		String t = request.getParameter("telefono");
		int tfno = Integer.parseInt(t);
		String codigopostal = request.getParameter("cp");
		int cp = Integer.parseInt(codigopostal);
		String pais = request.getParameter("pais");
		String pss = request.getParameter("pss");
		
		Distribuidor d = new Distribuidor(id, tfno, cp, nombre, domicilio, email, pais, null, pss, null);
		
		if(distribuidorDAO.actualizar(d) !=0) {
			
			session.setAttribute("usuario", d);
			request.setAttribute("dist", d);
			RequestDispatcher dispatcher =request.getRequestDispatcher("/usuarioModificado.jsp");
			
			dispatcher.forward(request, response);
		}else {
			
			
			request.setAttribute("dist", dist);
			RequestDispatcher dispatcher =request.getRequestDispatcher("/usuarioModificadoError.jsp");
			
			dispatcher.forward(request, response);
			
		}
			
			
		
		
		
	}

}
