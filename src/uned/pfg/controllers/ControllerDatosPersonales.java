package uned.pfg.controllers;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.bean.Distribuidor;
import uned.pfg.dao.DistribuidorDAO;


/**
 *Servlet para hacer de controlador  de los datos personales de un usuario que este dado de alta en el
 *sistema
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
@WebServlet("/ControllerDatosPersonales")
public class ControllerDatosPersonales extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private BasicDataSource basicDataSource;
	
	/*@Resource(name="jdbc/prueba")
	private DataSource pool;*/
	
	private DistribuidorDAO distribuidorDAO;


	/**
	 * Metodo que inicializa las variables del servlet
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}

	/**
	 * Metodo que representa al modo de conexion de la vista con el controlador y viceversa por el modo
	 * GET del servlet.
	 * 
	 * Manda toda la informacion del distribuidor que se ha logeado y ha creado la sesion a la vista
	 * cuentaUsuario.jsp para poder ver sus datos personales que tiene registrados en el sistema
	 * 
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
	 * Recoge todo los catos de todos los campos JTextField de la vista cuentaUsuario.jsp y actualiza
	 * los valores recogidos del usuario en la base de datos usando para ellos el objeto DAO 
	 * correspondiente al distribuidor y desde donde se hace la conexion con la discha base de datos.
	 * 
	 * Si la actualizacion  ha tenido problemas, muestra una pagina de error de no actualizacion de los
	 * datos perosonales. En el caso de que si se pueda modificar, muestra una pagina de exito en la
	 * modificacion
	 * 
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
