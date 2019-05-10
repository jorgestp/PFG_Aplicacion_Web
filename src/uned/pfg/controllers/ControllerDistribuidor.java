package uned.pfg.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.Distribuidor;
import uned.pfg.dao.DistribuidorDAO;

/**
 * Servlet implementation class ControllerDistribuidor
 */
@WebServlet("/ControllerDistribuidor")
public class ControllerDistribuidor extends HttpServlet {
	
	
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//doGet(request, response);
		
		String nombre = request.getParameter("nombre");
		String domicilio = request.getParameter("domicilio");
		String email = request.getParameter("email");
		String t = request.getParameter("telefono");
		int tfno = Integer.parseInt(t);
		String codigopostal = request.getParameter("cp");
		int cp = Integer.parseInt(codigopostal);
		String pais = request.getParameter("pais");
		String fecha = request.getParameter("date");
		
		SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = formatofecha.parse(fecha);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		String user = request.getParameter("user");
		String pss = request.getParameter("pss");
		
		Distribuidor dist = new Distribuidor(tfno, cp, nombre, domicilio, email, pais, user, pss, d);
		
		distribuidorDAO.insert(dist);
	}

}
