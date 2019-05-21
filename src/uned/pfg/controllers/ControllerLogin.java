package uned.pfg.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
 * Sevlet que sirve para logear a un usuario en el sistema.
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
@WebServlet("/ControllerLogin")
public class ControllerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	//@Resource(name="jdbc/prueba")
	//private DataSource pool;
	private BasicDataSource basicDataSource;
	private DistribuidorDAO distribuidorDAO;
	//private PedidoDAO pedidoDAO;


	/**
	 * Funcion que inicializa el objeto DAO de distribuidor.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		try {
			
			distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
			String path = config.getServletContext().getRealPath("WEB-INF\\XML.xml");
			//String path2 = config.getServletContext().getContextPath();  ESTO DA=> /APLICACION_WEB
			String path2 = config.getServletContext().getRealPath("/");
			File prueba = new File(path2 + "\\prueba.xml");
			
			System.out.println("RUTA DEL ARCHIVO CREADO => " + prueba.getPath());
			
			
			FileWriter fichero2 = null;
	        PrintWriter pw = null;
	        try
	        {
	            fichero2 = new FileWriter(prueba);
	            pw = new PrintWriter(fichero2);

	            for (int i = 0; i < 10; i++) {
	                pw.println("Linea " + (i+10));
	            }

	            fichero2.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        File archivo = null;
	        FileReader fr = null;
	        BufferedReader br = null;

	        try {
	           // Apertura del fichero y creacion de BufferedReader para poder
	           // hacer una lectura comoda (disponer del metodo readLine()).
	           archivo = new File (config.getServletContext().getRealPath("prueba.xml"));
	           fr = new FileReader (archivo);
	           br = new BufferedReader(fr);

	           // Lectura del fichero
	           String linea;
	           while((linea=br.readLine())!=null) {
	              System.out.println(linea);
	           }
	           fr.close();
	        }
	        
	        catch(Exception e){
	           e.printStackTrace();
	        }
			System.out.println("##########################");
			System.out.println("RUTA DEL DIRECTORIO RAIZ DEL PROYECTO => " +path2);
			System.out.println("##########################");
			System.out.println("RUTA ESPECIFICA => " +path);
			//pedidoDAO = new PedidoDAO(basicDataSource);
			
			String cadena;
			String fichero = "";
			
			FileReader f = new FileReader(path);
		    BufferedReader b = new BufferedReader(f);
		      while((cadena = b.readLine())!=null) {
		    	  fichero = fichero + cadena;
		    	  System.out.println(cadena);
		      }
		      b.close();
		}catch (Exception e) {
			
			throw new ServletException();
		}
	}



	/**
	 * Funcion que recoge el nick y la contraseña del usuario que se intenta logear en el sistema.
	 * 
	 * Llama a la funcion correspondiente dentro del objeto DAO del distribuidor, y si encuentra
	 * un distribuidor con ese nick y esa contraseña, quiere decir que el usuario se ha logeado correctamente
	 * y se le puede mandar a su cuenta de usuario con sus datos personales.
	 * En el caso de no encontrar ninguno, es devuelto a una vista de error en logearse.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String usuario = request.getParameter("usuario");
		String contra = request.getParameter("contra");
		
		Distribuidor dist = distribuidorDAO.buscarUsuario(usuario, contra);
		
		
		/*WS_Pedido ped = new WS_Pedido();
		
		String x = ped.envioPedido();
		
		System.out.println(x);*/
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
