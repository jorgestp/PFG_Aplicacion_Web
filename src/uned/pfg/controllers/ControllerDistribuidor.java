package uned.pfg.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp.BasicDataSource;
import uned.pfg.bean.Distribuidor;
import uned.pfg.dao.DistribuidorDAO;

/**
 * Servlet que se utiliza como controlador entre la vista de inserccion de nuevos clientes y el modelo 
 * del sistema, haciendo puente entre ellos y recogiendo los datos de la vista, pasarlos al modelo y viceversa
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
@WebServlet("/ControllerDistribuidor")
public class ControllerDistribuidor extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	private BasicDataSource basicDataSource;
	/*@Resource(name="jdbc/prueba")
	private DataSource pool;*/
	
	private DistribuidorDAO distribuidorDAO;


	/**
	 * Inicializa el objeto DAO correspondiente al distribuidor
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
	 * Metodo que recoge los parametros de un nuevo Distribuidor y los introduce en el sistema.
	 * 
	 * Recoge todos los campos requeridos de la vista, creando un objeto de tipo Distribuidor y llamando
	 * al metodo correspondiente del objeto DAO para insertar el distribuidor en la base de datos.
	 * 
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
